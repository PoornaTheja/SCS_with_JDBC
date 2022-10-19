package com.dbms.scs.controller;

import com.dbms.scs.model.CounselorDTO;
import com.dbms.scs.service.CounselorService;
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
@RequestMapping("/counselors")
public class CounselorController {

    private final CounselorService counselorService;

    public CounselorController(final CounselorService counselorService) {
        this.counselorService = counselorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("counselors", counselorService.findAll());
        return "counselor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("counselor") final CounselorDTO counselorDTO) {
        return "counselor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("counselor") @Valid final CounselorDTO counselorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counselor/add";
        }
        counselorService.create(counselorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counselor.create.success"));
        return "redirect:/counselors";
    }

    @GetMapping("/edit/{counselorId}")
    public String edit(@PathVariable final Integer counselorId, final Model model) {
        model.addAttribute("counselor", counselorService.get(counselorId));
        return "counselor/edit";
    }

    @PostMapping("/edit/{counselorId}")
    public String edit(@PathVariable final Integer counselorId,
            @ModelAttribute("counselor") @Valid final CounselorDTO counselorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counselor/edit";
        }
        counselorService.update(counselorId, counselorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counselor.update.success"));
        return "redirect:/counselors";
    }

    @PostMapping("/delete/{counselorId}")
    public String delete(@PathVariable final Integer counselorId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = counselorService.getReferencedWarning(counselorId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            counselorService.delete(counselorId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("counselor.delete.success"));
        }
        return "redirect:/counselors";
    }

}
