package com.dbms.scs.controller;

import com.dbms.scs.domain.Event;
import com.dbms.scs.domain.GuestsSpeakers;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.HonorariumDTO;
import com.dbms.scs.repos.EventRepository;
import com.dbms.scs.repos.GuestsSpeakersRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.service.HonorariumService;
import com.dbms.scs.util.WebUtils;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/honorariums")
public class HonorariumController {

    private final HonorariumService honorariumService;
    private final EventRepository eventRepository;
    private final GuestsSpeakersRepository guestsSpeakersRepository;
    private final SCSMembersRepository sCSMembersRepository;

    public HonorariumController(final HonorariumService honorariumService,
            final EventRepository eventRepository,
            final GuestsSpeakersRepository guestsSpeakersRepository,
            final SCSMembersRepository sCSMembersRepository) {
        this.honorariumService = honorariumService;
        this.eventRepository = eventRepository;
        this.guestsSpeakersRepository = guestsSpeakersRepository;
        this.sCSMembersRepository = sCSMembersRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("honoredForValues", eventRepository.findAll().stream().collect(
                Collectors.toMap(Event::getEventID, Event::getResources)));
        model.addAttribute("honoredToGuestsValues", guestsSpeakersRepository.findAll().stream().collect(
                Collectors.toMap(GuestsSpeakers::getGuestID, GuestsSpeakers::getName)));
        model.addAttribute("honorarirumResponsiblesValues", sCSMembersRepository.findAll().stream().collect(
                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("honorariums", honorariumService.findAll());
        return "honorarium/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("honorarium") final HonorariumDTO honorariumDTO) {
        return "honorarium/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("honorarium") @Valid final HonorariumDTO honorariumDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "honorarium/add";
        }
        honorariumService.create(honorariumDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("honorarium.create.success"));
        return "redirect:/honorariums";
    }

    @GetMapping("/edit/{honorariumID}")
    public String edit(@PathVariable final Integer honorariumID, final Model model) {
        model.addAttribute("honorarium", honorariumService.get(honorariumID));
        return "honorarium/edit";
    }

    @PostMapping("/edit/{honorariumID}")
    public String edit(@PathVariable final Integer honorariumID,
            @ModelAttribute("honorarium") @Valid final HonorariumDTO honorariumDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "honorarium/edit";
        }
        honorariumService.update(honorariumID, honorariumDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("honorarium.update.success"));
        return "redirect:/honorariums";
    }

    @PostMapping("/delete/{honorariumID}")
    public String delete(@PathVariable final Integer honorariumID,
            final RedirectAttributes redirectAttributes) {
        honorariumService.delete(honorariumID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("honorarium.delete.success"));
        return "redirect:/honorariums";
    }

}
