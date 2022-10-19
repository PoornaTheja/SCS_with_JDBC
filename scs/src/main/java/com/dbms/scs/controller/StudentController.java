package com.dbms.scs.controller;

import com.dbms.scs.domain.AlumniAdvisors;
import com.dbms.scs.domain.Award;
import com.dbms.scs.domain.Event;
import com.dbms.scs.domain.IMGroups;
import com.dbms.scs.domain.Inititatives;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.StudentDTO;
import com.dbms.scs.repos.AlumniAdvisorsRepository;
import com.dbms.scs.repos.AwardRepository;
import com.dbms.scs.repos.EventRepository;
import com.dbms.scs.repos.IMGroupsRepository;
import com.dbms.scs.repos.InititativesRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final AlumniAdvisorsRepository alumniAdvisorsRepository;
    private final SCSMembersRepository sCSMembersRepository;
    private final EventRepository eventRepository;
    private final AwardRepository awardRepository;
    private final InititativesRepository inititativesRepository;
    private final IMGroupsRepository iMGroupsRepository;

    public StudentController(final StudentService studentService,
            final AlumniAdvisorsRepository alumniAdvisorsRepository,
            final SCSMembersRepository sCSMembersRepository, final EventRepository eventRepository,
            final AwardRepository awardRepository,
            final InititativesRepository inititativesRepository,
            final IMGroupsRepository iMGroupsRepository) {
        this.studentService = studentService;
        this.alumniAdvisorsRepository = alumniAdvisorsRepository;
        this.sCSMembersRepository = sCSMembersRepository;
        this.eventRepository = eventRepository;
        this.awardRepository = awardRepository;
        this.inititativesRepository = inititativesRepository;
        this.iMGroupsRepository = iMGroupsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("studentPassedOutValues", alumniAdvisorsRepository.findAll().stream().collect(
                Collectors.toMap(AlumniAdvisors::getAlumniID, AlumniAdvisors::getAlumniID)));
        model.addAttribute("sCSMembershipValues", sCSMembersRepository.findAll().stream().collect(
                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
        model.addAttribute("studparticipateeventsValues", eventRepository.findAll().stream().collect(
                Collectors.toMap(Event::getEventID, Event::getResources)));
        model.addAttribute("studentawardedsValues", awardRepository.findAll().stream().collect(
                Collectors.toMap(Award::getAwardID, Award::getAwardType)));
        model.addAttribute("studinitiativepartsValues", inititativesRepository.findAll().stream().collect(
                Collectors.toMap(Inititatives::getInitiativesId, Inititatives::getTitle)));
        model.addAttribute("studspartofIMgpValues", iMGroupsRepository.findAll().stream().collect(
                Collectors.toMap(IMGroups::getGroupID, IMGroups::getGroupID)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("student") final StudentDTO studentDTO) {
        return "student/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("student") @Valid final StudentDTO studentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/add";
        }
        studentService.create(studentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.create.success"));
        return "redirect:/students";
    }

    @GetMapping("/edit/{studentRollNo}")
    public String edit(@PathVariable final Integer studentRollNo, final Model model) {
        model.addAttribute("student", studentService.get(studentRollNo));
        return "student/edit";
    }

    @PostMapping("/edit/{studentRollNo}")
    public String edit(@PathVariable final Integer studentRollNo,
            @ModelAttribute("student") @Valid final StudentDTO studentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/edit";
        }
        studentService.update(studentRollNo, studentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.update.success"));
        return "redirect:/students";
    }

    @PostMapping("/delete/{studentRollNo}")
    public String delete(@PathVariable final Integer studentRollNo,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = studentService.getReferencedWarning(studentRollNo);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            studentService.delete(studentRollNo);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("student.delete.success"));
        }
        return "redirect:/students";
    }

}
