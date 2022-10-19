package com.dbms.scs.controller;

import com.dbms.scs.model.AlumniAdvisorsDTO;
import com.dbms.scs.service.AlumniAdvisorsService;
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
@RequestMapping("/alumniAdvisorss")
public class AlumniAdvisorsController {

    private final AlumniAdvisorsService alumniAdvisorsService;

    public AlumniAdvisorsController(final AlumniAdvisorsService alumniAdvisorsService) {
        this.alumniAdvisorsService = alumniAdvisorsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("alumniAdvisorss", alumniAdvisorsService.findAll());
        return "alumniAdvisors/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("alumniAdvisors") final AlumniAdvisorsDTO alumniAdvisorsDTO) {
        return "alumniAdvisors/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("alumniAdvisors") @Valid final AlumniAdvisorsDTO alumniAdvisorsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "alumniAdvisors/add";
        }
        alumniAdvisorsService.create(alumniAdvisorsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("alumniAdvisors.create.success"));
        return "redirect:/alumniAdvisorss";
    }

    @GetMapping("/edit/{alumniID}")
    public String edit(@PathVariable final Integer alumniID, final Model model) {
        model.addAttribute("alumniAdvisors", alumniAdvisorsService.get(alumniID));
        return "alumniAdvisors/edit";
    }

    @PostMapping("/edit/{alumniID}")
    public String edit(@PathVariable final Integer alumniID,
            @ModelAttribute("alumniAdvisors") @Valid final AlumniAdvisorsDTO alumniAdvisorsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "alumniAdvisors/edit";
        }
        alumniAdvisorsService.update(alumniID, alumniAdvisorsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("alumniAdvisors.update.success"));
        return "redirect:/alumniAdvisorss";
    }

    @PostMapping("/delete/{alumniID}")
    public String delete(@PathVariable final Integer alumniID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = alumniAdvisorsService.getReferencedWarning(alumniID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            alumniAdvisorsService.delete(alumniID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("alumniAdvisors.delete.success"));
        }
        return "redirect:/alumniAdvisorss";
    }

}
