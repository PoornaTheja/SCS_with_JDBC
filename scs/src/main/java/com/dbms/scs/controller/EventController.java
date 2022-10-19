package com.dbms.scs.controller;

import com.dbms.scs.domain.EventsPermission;
import com.dbms.scs.domain.Venue;
import com.dbms.scs.model.EventDTO;
import com.dbms.scs.repos.EventsPermissionRepository;
import com.dbms.scs.repos.VenueRepository;
import com.dbms.scs.service.EventService;
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
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final VenueRepository venueRepository;
    private final EventsPermissionRepository eventsPermissionRepository;

    public EventController(final EventService eventService, final VenueRepository venueRepository,
            final EventsPermissionRepository eventsPermissionRepository) {
        this.eventService = eventService;
        this.venueRepository = venueRepository;
        this.eventsPermissionRepository = eventsPermissionRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("venuedAtValues", venueRepository.findAll().stream().collect(
                Collectors.toMap(Venue::getVenueID, Venue::getVenueName)));
        model.addAttribute("seekpermissionValues", eventsPermissionRepository.findAll().stream().collect(
                Collectors.toMap(EventsPermission::getPermissionID, EventsPermission::getAttestedBy)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("events", eventService.findAll());
        return "event/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("event") final EventDTO eventDTO) {
        return "event/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") @Valid final EventDTO eventDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/add";
        }
        eventService.create(eventDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.create.success"));
        return "redirect:/events";
    }

    @GetMapping("/edit/{eventID}")
    public String edit(@PathVariable final Integer eventID, final Model model) {
        model.addAttribute("event", eventService.get(eventID));
        return "event/edit";
    }

    @PostMapping("/edit/{eventID}")
    public String edit(@PathVariable final Integer eventID,
            @ModelAttribute("event") @Valid final EventDTO eventDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/edit";
        }
        eventService.update(eventID, eventDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.update.success"));
        return "redirect:/events";
    }

    @PostMapping("/delete/{eventID}")
    public String delete(@PathVariable final Integer eventID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = eventService.getReferencedWarning(eventID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            eventService.delete(eventID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("event.delete.success"));
        }
        return "redirect:/events";
    }

}
