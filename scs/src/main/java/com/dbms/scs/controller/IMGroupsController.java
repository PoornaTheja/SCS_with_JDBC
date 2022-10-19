package com.dbms.scs.controller;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.InductionProgram;
import com.dbms.scs.model.IMGroupsDTO;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.InductionProgramRepository;
import com.dbms.scs.service.IMGroupsService;
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
@RequestMapping("/iMGroupss")
public class IMGroupsController {

    private final IMGroupsService iMGroupsService;
    private final InductionProgramRepository inductionProgramRepository;
    private final FacultyRepository facultyRepository;

    public IMGroupsController(final IMGroupsService iMGroupsService,
            final InductionProgramRepository inductionProgramRepository,
            final FacultyRepository facultyRepository) {
        this.iMGroupsService = iMGroupsService;
        this.inductionProgramRepository = inductionProgramRepository;
        this.facultyRepository = facultyRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("iMgroupsinprogramValues", inductionProgramRepository.findAll().stream().collect(
                Collectors.toMap(InductionProgram::getInductionId, InductionProgram::getDuration)));
        model.addAttribute("iMgrpssupervisedbyFacValues", facultyRepository.findAll().stream().collect(
                Collectors.toMap(Faculty::getFacultyId, Faculty::getDepartment)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("iMGroupss", iMGroupsService.findAll());
        return "iMGroups/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("iMGroups") final IMGroupsDTO iMGroupsDTO) {
        return "iMGroups/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("iMGroups") @Valid final IMGroupsDTO iMGroupsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "iMGroups/add";
        }
        iMGroupsService.create(iMGroupsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("iMGroups.create.success"));
        return "redirect:/iMGroupss";
    }

    @GetMapping("/edit/{groupID}")
    public String edit(@PathVariable final Integer groupID, final Model model) {
        model.addAttribute("iMGroups", iMGroupsService.get(groupID));
        return "iMGroups/edit";
    }

    @PostMapping("/edit/{groupID}")
    public String edit(@PathVariable final Integer groupID,
            @ModelAttribute("iMGroups") @Valid final IMGroupsDTO iMGroupsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "iMGroups/edit";
        }
        iMGroupsService.update(groupID, iMGroupsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("iMGroups.update.success"));
        return "redirect:/iMGroupss";
    }

    @PostMapping("/delete/{groupID}")
    public String delete(@PathVariable final Integer groupID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = iMGroupsService.getReferencedWarning(groupID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            iMGroupsService.delete(groupID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("iMGroups.delete.success"));
        }
        return "redirect:/iMGroupss";
    }

}
