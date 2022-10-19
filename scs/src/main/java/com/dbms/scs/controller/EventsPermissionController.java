package com.dbms.scs.controller;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.EventsPermissionDTO;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.service.EventsPermissionService;
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
@RequestMapping("/eventsPermissions")
public class EventsPermissionController {

    private final EventsPermissionService eventsPermissionService;
    private final FacultyRepository facultyRepository;
    private final SCSMembersRepository sCSMembersRepository;

    public EventsPermissionController(final EventsPermissionService eventsPermissionService,
            final FacultyRepository facultyRepository,
            final SCSMembersRepository sCSMembersRepository) {
        this.eventsPermissionService = eventsPermissionService;
        this.facultyRepository = facultyRepository;
        this.sCSMembersRepository = sCSMembersRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("permissiongrantedbyValues", facultyRepository.findAll().stream().collect(
                Collectors.toMap(Faculty::getFacultyId, Faculty::getDepartment)));
        model.addAttribute("permissionattestedbyValues", facultyRepository.findAll().stream().collect(
                Collectors.toMap(Faculty::getFacultyId, Faculty::getDepartment)));
        model.addAttribute("membertakespermissionValues", sCSMembersRepository.findAll().stream().collect(
                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("eventsPermissions", eventsPermissionService.findAll());
        return "eventsPermission/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("eventsPermission") final EventsPermissionDTO eventsPermissionDTO) {
        return "eventsPermission/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("eventsPermission") @Valid final EventsPermissionDTO eventsPermissionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "eventsPermission/add";
        }
        eventsPermissionService.create(eventsPermissionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventsPermission.create.success"));
        return "redirect:/eventsPermissions";
    }

    @GetMapping("/edit/{permissionID}")
    public String edit(@PathVariable final Integer permissionID, final Model model) {
        model.addAttribute("eventsPermission", eventsPermissionService.get(permissionID));
        return "eventsPermission/edit";
    }

    @PostMapping("/edit/{permissionID}")
    public String edit(@PathVariable final Integer permissionID,
            @ModelAttribute("eventsPermission") @Valid final EventsPermissionDTO eventsPermissionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "eventsPermission/edit";
        }
        eventsPermissionService.update(permissionID, eventsPermissionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("eventsPermission.update.success"));
        return "redirect:/eventsPermissions";
    }

    @PostMapping("/delete/{permissionID}")
    public String delete(@PathVariable final Integer permissionID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = eventsPermissionService.getReferencedWarning(permissionID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            eventsPermissionService.delete(permissionID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("eventsPermission.delete.success"));
        }
        return "redirect:/eventsPermissions";
    }

}
