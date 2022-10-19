package com.dbms.scs.controller;

import com.dbms.scs.model.YearlyBudgetsDTO;
import com.dbms.scs.service.YearlyBudgetsService;
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
@RequestMapping("/yearlyBudgetss")
public class YearlyBudgetsController {

    private final YearlyBudgetsService yearlyBudgetsService;

    public YearlyBudgetsController(final YearlyBudgetsService yearlyBudgetsService) {
        this.yearlyBudgetsService = yearlyBudgetsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("yearlyBudgetss", yearlyBudgetsService.findAll());
        return "yearlyBudgets/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("yearlyBudgets") final YearlyBudgetsDTO yearlyBudgetsDTO) {
        return "yearlyBudgets/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("yearlyBudgets") @Valid final YearlyBudgetsDTO yearlyBudgetsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "yearlyBudgets/add";
        }
        yearlyBudgetsService.create(yearlyBudgetsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("yearlyBudgets.create.success"));
        return "redirect:/yearlyBudgetss";
    }

    @GetMapping("/edit/{verticalID}")
    public String edit(@PathVariable final Integer verticalID, final Model model) {
        model.addAttribute("yearlyBudgets", yearlyBudgetsService.get(verticalID));
        return "yearlyBudgets/edit";
    }

    @PostMapping("/edit/{verticalID}")
    public String edit(@PathVariable final Integer verticalID,
            @ModelAttribute("yearlyBudgets") @Valid final YearlyBudgetsDTO yearlyBudgetsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "yearlyBudgets/edit";
        }
        yearlyBudgetsService.update(verticalID, yearlyBudgetsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("yearlyBudgets.update.success"));
        return "redirect:/yearlyBudgetss";
    }

    @PostMapping("/delete/{verticalID}")
    public String delete(@PathVariable final Integer verticalID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = yearlyBudgetsService.getReferencedWarning(verticalID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            yearlyBudgetsService.delete(verticalID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("yearlyBudgets.delete.success"));
        }
        return "redirect:/yearlyBudgetss";
    }

}
