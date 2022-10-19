package com.dbms.scs.controller;

import com.dbms.scs.model.InductionProgramDTO;
import com.dbms.scs.service.InductionProgramService;
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
@RequestMapping("/inductionPrograms")
public class InductionProgramController {

    private final InductionProgramService inductionProgramService;

    public InductionProgramController(final InductionProgramService inductionProgramService) {
        this.inductionProgramService = inductionProgramService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inductionPrograms", inductionProgramService.findAll());
        return "inductionProgram/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("inductionProgram") final InductionProgramDTO inductionProgramDTO) {
        return "inductionProgram/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("inductionProgram") @Valid final InductionProgramDTO inductionProgramDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionProgram/add";
        }
        inductionProgramService.create(inductionProgramDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionProgram.create.success"));
        return "redirect:/inductionPrograms";
    }

    @GetMapping("/edit/{inductionId}")
    public String edit(@PathVariable final Integer inductionId, final Model model) {
        model.addAttribute("inductionProgram", inductionProgramService.get(inductionId));
        return "inductionProgram/edit";
    }

    @PostMapping("/edit/{inductionId}")
    public String edit(@PathVariable final Integer inductionId,
            @ModelAttribute("inductionProgram") @Valid final InductionProgramDTO inductionProgramDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionProgram/edit";
        }
        inductionProgramService.update(inductionId, inductionProgramDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionProgram.update.success"));
        return "redirect:/inductionPrograms";
    }

    @PostMapping("/delete/{inductionId}")
    public String delete(@PathVariable final Integer inductionId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inductionProgramService.getReferencedWarning(inductionId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inductionProgramService.delete(inductionId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inductionProgram.delete.success"));
        }
        return "redirect:/inductionPrograms";
    }

}
