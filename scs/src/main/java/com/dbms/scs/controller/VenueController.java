package com.dbms.scs.controller;

import com.dbms.scs.model.VenueDTO;
import com.dbms.scs.service.VenueService;
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
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(final VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("venues", venueService.findAll());
        return "venue/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("venue") final VenueDTO venueDTO) {
        return "venue/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("venue") @Valid final VenueDTO venueDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "venue/add";
        }
        venueService.create(venueDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("venue.create.success"));
        return "redirect:/venues";
    }

    @GetMapping("/edit/{venueID}")
    public String edit(@PathVariable final Integer venueID, final Model model) {
        model.addAttribute("venue", venueService.get(venueID));
        return "venue/edit";
    }

    @PostMapping("/edit/{venueID}")
    public String edit(@PathVariable final Integer venueID,
            @ModelAttribute("venue") @Valid final VenueDTO venueDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "venue/edit";
        }
        venueService.update(venueID, venueDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("venue.update.success"));
        return "redirect:/venues";
    }

    @PostMapping("/delete/{venueID}")
    public String delete(@PathVariable final Integer venueID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = venueService.getReferencedWarning(venueID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            venueService.delete(venueID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("venue.delete.success"));
        }
        return "redirect:/venues";
    }

}
