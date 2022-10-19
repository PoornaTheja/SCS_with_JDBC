package com.dbms.scs.controller;

import com.dbms.scs.model.GuestsSpeakersDTO;
import com.dbms.scs.service.GuestsSpeakersService;
import com.dbms.scs.util.WebUtils;
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
@RequestMapping("/guestsSpeakerss")
public class GuestsSpeakersController {

    private final GuestsSpeakersService guestsSpeakersService;

    public GuestsSpeakersController(final GuestsSpeakersService guestsSpeakersService) {
        this.guestsSpeakersService = guestsSpeakersService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("guestsSpeakerss", guestsSpeakersService.findAll());
        return "guestsSpeakers/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("guestsSpeakers") final GuestsSpeakersDTO guestsSpeakersDTO) {
        return "guestsSpeakers/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("guestsSpeakers") @Valid final GuestsSpeakersDTO guestsSpeakersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "guestsSpeakers/add";
        }
        guestsSpeakersService.create(guestsSpeakersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("guestsSpeakers.create.success"));
        return "redirect:/guestsSpeakerss";
    }

    @GetMapping("/edit/{guestID}")
    public String edit(@PathVariable final Integer guestID, final Model model) {
        model.addAttribute("guestsSpeakers", guestsSpeakersService.get(guestID));
        return "guestsSpeakers/edit";
    }

    @PostMapping("/edit/{guestID}")
    public String edit(@PathVariable final Integer guestID,
            @ModelAttribute("guestsSpeakers") @Valid final GuestsSpeakersDTO guestsSpeakersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "guestsSpeakers/edit";
        }
        guestsSpeakersService.update(guestID, guestsSpeakersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("guestsSpeakers.update.success"));
        return "redirect:/guestsSpeakerss";
    }

    @PostMapping("/delete/{guestID}")
    public String delete(@PathVariable final Integer guestID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = guestsSpeakersService.getReferencedWarning(guestID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            guestsSpeakersService.delete(guestID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("guestsSpeakers.delete.success"));
        }
        return "redirect:/guestsSpeakerss";
    }

}
