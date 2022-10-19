package com.dbms.scs.controller;

import com.dbms.scs.domain.Student;
import com.dbms.scs.model.MonthlyReportsDTO;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.service.MonthlyReportsService;
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
@RequestMapping("/monthlyReportss")
public class MonthlyReportsController {

    private final MonthlyReportsService monthlyReportsService;
    private final StudentRepository studentRepository;

    public MonthlyReportsController(final MonthlyReportsService monthlyReportsService,
            final StudentRepository studentRepository) {
        this.monthlyReportsService = monthlyReportsService;
        this.studentRepository = studentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("studntcompilesreportsValues", studentRepository.findAll().stream().collect(
                Collectors.toMap(Student::getStudentRollNo, Student::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("monthlyReportss", monthlyReportsService.findAll());
        return "monthlyReports/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("monthlyReports") final MonthlyReportsDTO monthlyReportsDTO) {
        return "monthlyReports/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("monthlyReports") @Valid final MonthlyReportsDTO monthlyReportsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "monthlyReports/add";
        }
        monthlyReportsService.create(monthlyReportsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("monthlyReports.create.success"));
        return "redirect:/monthlyReportss";
    }

    @GetMapping("/edit/{reportID}")
    public String edit(@PathVariable final Integer reportID, final Model model) {
        model.addAttribute("monthlyReports", monthlyReportsService.get(reportID));
        return "monthlyReports/edit";
    }

    @PostMapping("/edit/{reportID}")
    public String edit(@PathVariable final Integer reportID,
            @ModelAttribute("monthlyReports") @Valid final MonthlyReportsDTO monthlyReportsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "monthlyReports/edit";
        }
        monthlyReportsService.update(reportID, monthlyReportsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("monthlyReports.update.success"));
        return "redirect:/monthlyReportss";
    }

    @PostMapping("/delete/{reportID}")
    public String delete(@PathVariable final Integer reportID,
            final RedirectAttributes redirectAttributes) {
        monthlyReportsService.delete(reportID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("monthlyReports.delete.success"));
        return "redirect:/monthlyReportss";
    }

}
