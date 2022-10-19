package com.dbms.scs.controller;

import com.dbms.scs.model.RecruitmentDTO;
import com.dbms.scs.service.RecruitmentService;
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
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    public RecruitmentController(final RecruitmentService recruitmentService) {
        this.recruitmentService = recruitmentService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("recruitments", recruitmentService.findAll());
        return "recruitment/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("recruitment") final RecruitmentDTO recruitmentDTO) {
        return "recruitment/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("recruitment") @Valid final RecruitmentDTO recruitmentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recruitment/add";
        }
        recruitmentService.create(recruitmentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("recruitment.create.success"));
        return "redirect:/recruitments";
    }

    @GetMapping("/edit/{recruitmentID}")
    public String edit(@PathVariable final Integer recruitmentID, final Model model) {
        model.addAttribute("recruitment", recruitmentService.get(recruitmentID));
        return "recruitment/edit";
    }

    @PostMapping("/edit/{recruitmentID}")
    public String edit(@PathVariable final Integer recruitmentID,
            @ModelAttribute("recruitment") @Valid final RecruitmentDTO recruitmentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recruitment/edit";
        }
        recruitmentService.update(recruitmentID, recruitmentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("recruitment.update.success"));
        return "redirect:/recruitments";
    }

    @PostMapping("/delete/{recruitmentID}")
    public String delete(@PathVariable final Integer recruitmentID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = recruitmentService.getReferencedWarning(recruitmentID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            recruitmentService.delete(recruitmentID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("recruitment.delete.success"));
        }
        return "redirect:/recruitments";
    }

}
