package com.dbms.scs.controller;

import com.dbms.scs.domain.InitiativesPermission;
import com.dbms.scs.domain.Venue;
import com.dbms.scs.model.InititativesDTO;
import com.dbms.scs.repos.InitiativesPermissionRepository;
import com.dbms.scs.repos.VenueRepository;
import com.dbms.scs.service.InititativesService;
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
@RequestMapping("/inititativess")
public class InititativesController {

    private final InititativesService inititativesService;
    private final InitiativesPermissionRepository initiativesPermissionRepository;
    private final VenueRepository venueRepository;

    public InititativesController(final InititativesService inititativesService,
            final InitiativesPermissionRepository initiativesPermissionRepository,
            final VenueRepository venueRepository) {
        this.inititativesService = inititativesService;
        this.initiativesPermissionRepository = initiativesPermissionRepository;
        this.venueRepository = venueRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("permissionforinitiativeValues", initiativesPermissionRepository.findAll().stream().collect(
                Collectors.toMap(InitiativesPermission::getPermissionID, InitiativesPermission::getAttestedBy)));
        model.addAttribute("initiatvehostedatValues", venueRepository.findAll().stream().collect(
                Collectors.toMap(Venue::getVenueID, Venue::getVenueName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inititativess", inititativesService.findAll());
        return "inititatives/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("inititatives") final InititativesDTO inititativesDTO) {
        return "inititatives/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("inititatives") @Valid final InititativesDTO inititativesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inititatives/add";
        }
        inititativesService.create(inititativesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inititatives.create.success"));
        return "redirect:/inititativess";
    }

    @GetMapping("/edit/{initiativesId}")
    public String edit(@PathVariable final Integer initiativesId, final Model model) {
        model.addAttribute("inititatives", inititativesService.get(initiativesId));
        return "inititatives/edit";
    }

    @PostMapping("/edit/{initiativesId}")
    public String edit(@PathVariable final Integer initiativesId,
            @ModelAttribute("inititatives") @Valid final InititativesDTO inititativesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inititatives/edit";
        }
        inititativesService.update(initiativesId, inititativesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inititatives.update.success"));
        return "redirect:/inititativess";
    }

    @PostMapping("/delete/{initiativesId}")
    public String delete(@PathVariable final Integer initiativesId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inititativesService.getReferencedWarning(initiativesId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inititativesService.delete(initiativesId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inititatives.delete.success"));
        }
        return "redirect:/inititativess";
    }

}
