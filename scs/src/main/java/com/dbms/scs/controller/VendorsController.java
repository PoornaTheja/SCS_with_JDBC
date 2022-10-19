package com.dbms.scs.controller;

import com.dbms.scs.model.VendorsDTO;
import com.dbms.scs.service.VendorsService;
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
@RequestMapping("/vendorss")
public class VendorsController {

    private final VendorsService vendorsService;

    public VendorsController(final VendorsService vendorsService) {
        this.vendorsService = vendorsService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("vendorss", vendorsService.findAll());
        return "vendors/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("vendors") final VendorsDTO vendorsDTO) {
        return "vendors/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("vendors") @Valid final VendorsDTO vendorsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vendors/add";
        }
        vendorsService.create(vendorsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vendors.create.success"));
        return "redirect:/vendorss";
    }

    @GetMapping("/edit/{vendorID}")
    public String edit(@PathVariable final Integer vendorID, final Model model) {
        model.addAttribute("vendors", vendorsService.get(vendorID));
        return "vendors/edit";
    }

    @PostMapping("/edit/{vendorID}")
    public String edit(@PathVariable final Integer vendorID,
            @ModelAttribute("vendors") @Valid final VendorsDTO vendorsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "vendors/edit";
        }
        vendorsService.update(vendorID, vendorsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("vendors.update.success"));
        return "redirect:/vendorss";
    }

    @PostMapping("/delete/{vendorID}")
    public String delete(@PathVariable final Integer vendorID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = vendorsService.getReferencedWarning(vendorID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            vendorsService.delete(vendorID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("vendors.delete.success"));
        }
        return "redirect:/vendorss";
    }

}
