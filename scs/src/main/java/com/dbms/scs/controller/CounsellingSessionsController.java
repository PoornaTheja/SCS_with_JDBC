package com.dbms.scs.controller;

import com.dbms.scs.domain.Counselor;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.CounsellingSessionsDTO;
import com.dbms.scs.repos.CounselorRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.service.CounsellingSessionsService;
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
@RequestMapping("/counsellingSessionss")
public class CounsellingSessionsController {

    private final CounsellingSessionsService counsellingSessionsService;
    private final CounselorRepository counselorRepository;
    private final StudentRepository studentRepository;

    public CounsellingSessionsController(
            final CounsellingSessionsService counsellingSessionsService,
            final CounselorRepository counselorRepository,
            final StudentRepository studentRepository) {
        this.counsellingSessionsService = counsellingSessionsService;
        this.counselorRepository = counselorRepository;
        this.studentRepository = studentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("counseledByValues", counselorRepository.findAll().stream().collect(
                Collectors.toMap(Counselor::getCounselorId, Counselor::getName)));
        model.addAttribute("counselsToValues", studentRepository.findAll().stream().collect(
                Collectors.toMap(Student::getStudentRollNo, Student::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("counsellingSessionss", counsellingSessionsService.findAll());
        return "counsellingSessions/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("counsellingSessions") final CounsellingSessionsDTO counsellingSessionsDTO) {
        return "counsellingSessions/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("counsellingSessions") @Valid final CounsellingSessionsDTO counsellingSessionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counsellingSessions/add";
        }
        counsellingSessionsService.create(counsellingSessionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counsellingSessions.create.success"));
        return "redirect:/counsellingSessionss";
    }

    @GetMapping("/edit/{sessionID}")
    public String edit(@PathVariable final Integer sessionID, final Model model) {
        model.addAttribute("counsellingSessions", counsellingSessionsService.get(sessionID));
        return "counsellingSessions/edit";
    }

    @PostMapping("/edit/{sessionID}")
    public String edit(@PathVariable final Integer sessionID,
            @ModelAttribute("counsellingSessions") @Valid final CounsellingSessionsDTO counsellingSessionsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counsellingSessions/edit";
        }
        counsellingSessionsService.update(sessionID, counsellingSessionsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counsellingSessions.update.success"));
        return "redirect:/counsellingSessionss";
    }

    @PostMapping("/delete/{sessionID}")
    public String delete(@PathVariable final Integer sessionID,
            final RedirectAttributes redirectAttributes) {
        counsellingSessionsService.delete(sessionID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("counsellingSessions.delete.success"));
        return "redirect:/counsellingSessionss";
    }

}
