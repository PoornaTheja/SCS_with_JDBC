package com.dbms.scs.controller;

import com.dbms.scs.domain.YearlyBudgets;
import com.dbms.scs.model.VerticalDTO;
import com.dbms.scs.repos.YearlyBudgetsRepository;
import com.dbms.scs.service.VerticalService;
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
@RequestMapping("/verticals")
public class VerticalController {

    private final VerticalService verticalService;
    private final YearlyBudgetsRepository yearlyBudgetsRepository;

    public VerticalController(final VerticalService verticalService,
            final YearlyBudgetsRepository yearlyBudgetsRepository) {
        this.verticalService = verticalService;
        this.yearlyBudgetsRepository = yearlyBudgetsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("verticalBudgetsValues", yearlyBudgetsRepository.findAll().stream().collect(
                Collectors.toMap(YearlyBudgets::getVerticalID, YearlyBudgets::getVerticalID)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("verticals", verticalService.findAll());
        return "vertical/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("vertical") final VerticalDTO verticalDTO) {
        return "vertical/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vertical") @Valid final VerticalDTO verticalDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vertical/add";
        }
        verticalService.create(verticalDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vertical.create.success"));
        return "redirect:/verticals";
    }

    @GetMapping("/edit/{verticalID}")
    public String edit(@PathVariable final Integer verticalID, final Model model) {
        model.addAttribute("vertical", verticalService.get(verticalID));
        return "vertical/edit";
    }

    @PostMapping("/edit/{verticalID}")
    public String edit(@PathVariable final Integer verticalID,
            @ModelAttribute("vertical") @Valid final VerticalDTO verticalDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vertical/edit";
        }
        verticalService.update(verticalID, verticalDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vertical.update.success"));
        return "redirect:/verticals";
    }

    @PostMapping("/delete/{verticalID}")
    public String delete(@PathVariable final Integer verticalID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = verticalService.getReferencedWarning(verticalID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            verticalService.delete(verticalID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("vertical.delete.success"));
        }
        return "redirect:/verticals";
    }

}
