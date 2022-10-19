package com.dbms.scs.controller;

import com.dbms.scs.domain.Vendors;
import com.dbms.scs.model.BillsDTO;
import com.dbms.scs.repos.VendorsRepository;
import com.dbms.scs.service.BillsService;
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
@RequestMapping("/billss")
public class BillsController {

    private final BillsService billsService;
    private final VendorsRepository vendorsRepository;

    public BillsController(final BillsService billsService,
            final VendorsRepository vendorsRepository) {
        this.billsService = billsService;
        this.vendorsRepository = vendorsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("vendorbilledbyValues", vendorsRepository.findAll().stream().collect(
                Collectors.toMap(Vendors::getVendorID, Vendors::getVendorName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("billss", billsService.findAll());
        return "bills/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bills") final BillsDTO billsDTO) {
        return "bills/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("bills") @Valid final BillsDTO billsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bills/add";
        }
        billsService.create(billsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bills.create.success"));
        return "redirect:/billss";
    }

    @GetMapping("/edit/{billID}")
    public String edit(@PathVariable final Integer billID, final Model model) {
        model.addAttribute("bills", billsService.get(billID));
        return "bills/edit";
    }

    @PostMapping("/edit/{billID}")
    public String edit(@PathVariable final Integer billID,
            @ModelAttribute("bills") @Valid final BillsDTO billsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bills/edit";
        }
        billsService.update(billID, billsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bills.update.success"));
        return "redirect:/billss";
    }

    @PostMapping("/delete/{billID}")
    public String delete(@PathVariable final Integer billID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = billsService.getReferencedWarning(billID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            billsService.delete(billID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bills.delete.success"));
        }
        return "redirect:/billss";
    }

}
