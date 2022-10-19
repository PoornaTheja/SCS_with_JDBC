package com.dbms.scs.controller;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.InitiativesPermissionDTO;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.service.InitiativesPermissionService;
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
@RequestMapping("/initiativesPermissions")
public class InitiativesPermissionController {

    private final InitiativesPermissionService initiativesPermissionService;
    private final SCSMembersRepository sCSMembersRepository;
    private final FacultyRepository facultyRepository;

    public InitiativesPermissionController(
            final InitiativesPermissionService initiativesPermissionService,
            final SCSMembersRepository sCSMembersRepository,
            final FacultyRepository facultyRepository) {
        this.initiativesPermissionService = initiativesPermissionService;
        this.sCSMembersRepository = sCSMembersRepository;
        this.facultyRepository = facultyRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("memberseekinitiativepermissionValues", sCSMembersRepository.findAll().stream().collect(
                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
        model.addAttribute("initpermissgrantedValues", facultyRepository.findAll().stream().collect(
                Collectors.toMap(Faculty::getFacultyId, Faculty::getDepartment)));
        model.addAttribute("initpermissattestedbyValues", facultyRepository.findAll().stream().collect(
                Collectors.toMap(Faculty::getFacultyId, Faculty::getDepartment)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("initiativesPermissions", initiativesPermissionService.findAll());
        return "initiativesPermission/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("initiativesPermission") final InitiativesPermissionDTO initiativesPermissionDTO) {
        return "initiativesPermission/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("initiativesPermission") @Valid final InitiativesPermissionDTO initiativesPermissionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "initiativesPermission/add";
        }
        initiativesPermissionService.create(initiativesPermissionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("initiativesPermission.create.success"));
        return "redirect:/initiativesPermissions";
    }

    @GetMapping("/edit/{permissionID}")
    public String edit(@PathVariable final Integer permissionID, final Model model) {
        model.addAttribute("initiativesPermission", initiativesPermissionService.get(permissionID));
        return "initiativesPermission/edit";
    }

    @PostMapping("/edit/{permissionID}")
    public String edit(@PathVariable final Integer permissionID,
            @ModelAttribute("initiativesPermission") @Valid final InitiativesPermissionDTO initiativesPermissionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "initiativesPermission/edit";
        }
        initiativesPermissionService.update(permissionID, initiativesPermissionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("initiativesPermission.update.success"));
        return "redirect:/initiativesPermissions";
    }

    @PostMapping("/delete/{permissionID}")
    public String delete(@PathVariable final Integer permissionID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = initiativesPermissionService.getReferencedWarning(permissionID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            initiativesPermissionService.delete(permissionID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("initiativesPermission.delete.success"));
        }
        return "redirect:/initiativesPermissions";
    }

}
