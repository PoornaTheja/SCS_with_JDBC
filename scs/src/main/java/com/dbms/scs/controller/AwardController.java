package com.dbms.scs.controller;

import com.dbms.scs.model.AwardDTO;
import com.dbms.scs.service.AwardService;
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
@RequestMapping("/awards")
public class AwardController {

    private final AwardService awardService;

    public AwardController(final AwardService awardService) {
        this.awardService = awardService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("awards", awardService.findAll());
        return "award/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("award") final AwardDTO awardDTO) {
        return "award/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("award") @Valid final AwardDTO awardDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "award/add";
        }
        awardService.create(awardDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("award.create.success"));
        return "redirect:/awards";
    }

    @GetMapping("/edit/{awardID}")
    public String edit(@PathVariable final Integer awardID, final Model model) {
        model.addAttribute("award", awardService.get(awardID));
        return "award/edit";
    }

    @PostMapping("/edit/{awardID}")
    public String edit(@PathVariable final Integer awardID,
            @ModelAttribute("award") @Valid final AwardDTO awardDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "award/edit";
        }
        awardService.update(awardID, awardDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("award.update.success"));
        return "redirect:/awards";
    }

    @PostMapping("/delete/{awardID}")
    public String delete(@PathVariable final Integer awardID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = awardService.getReferencedWarning(awardID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            awardService.delete(awardID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("award.delete.success"));
        }
        return "redirect:/awards";
    }

}
