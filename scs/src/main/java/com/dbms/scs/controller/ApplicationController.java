package com.dbms.scs.controller;

import com.dbms.scs.domain.Recruitment;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.domain.Student;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.ApplicationDTO;
import com.dbms.scs.repos.RecruitmentRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.service.ApplicationService;
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
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final VerticalRepository verticalRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final SCSMembersRepository sCSMembersRepository;
    private final StudentRepository studentRepository;

    public ApplicationController(final ApplicationService applicationService,
            final VerticalRepository verticalRepository,
            final RecruitmentRepository recruitmentRepository,
            final SCSMembersRepository sCSMembersRepository,
            final StudentRepository studentRepository) {
        this.applicationService = applicationService;
        this.verticalRepository = verticalRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.sCSMembersRepository = sCSMembersRepository;
        this.studentRepository = studentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("applicationstoverticalValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
        model.addAttribute("applicnforrecruitmentValues", recruitmentRepository.findAll().stream().collect(
                Collectors.toMap(Recruitment::getRecruitmentID, Recruitment::getPOR)));
        model.addAttribute("applicantbecomesmemberValues", sCSMembersRepository.findAll().stream().collect(
                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
        model.addAttribute("studappliesValues", studentRepository.findAll().stream().collect(
                Collectors.toMap(Student::getStudentRollNo, Student::getName)));
        model.addAttribute("applicnpartofverticalValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("applications", applicationService.findAll());
        return "application/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("application") final ApplicationDTO applicationDTO) {
        return "application/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("application") @Valid final ApplicationDTO applicationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "application/add";
        }
        applicationService.create(applicationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("application.create.success"));
        return "redirect:/applications";
    }

    @GetMapping("/edit/{applicationID}")
    public String edit(@PathVariable final Long applicationID, final Model model) {
        model.addAttribute("application", applicationService.get(applicationID));
        return "application/edit";
    }

    @PostMapping("/edit/{applicationID}")
    public String edit(@PathVariable final Long applicationID,
            @ModelAttribute("application") @Valid final ApplicationDTO applicationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "application/edit";
        }
        applicationService.update(applicationID, applicationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("application.update.success"));
        return "redirect:/applications";
    }

    @PostMapping("/delete/{applicationID}")
    public String delete(@PathVariable final Long applicationID,
            final RedirectAttributes redirectAttributes) {
        applicationService.delete(applicationID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("application.delete.success"));
        return "redirect:/applications";
    }

}
