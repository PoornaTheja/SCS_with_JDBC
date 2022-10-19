package com.dbms.scs.controller;

import com.dbms.scs.domain.IMGroups;
import com.dbms.scs.domain.InductionProgram;
import com.dbms.scs.domain.Inductionmentor;
import com.dbms.scs.model.InductionmentorDTO;
import com.dbms.scs.repos.IMGroupsRepository;
import com.dbms.scs.repos.InductionProgramRepository;
import com.dbms.scs.repos.InductionmentorRepository;
import com.dbms.scs.service.InductionmentorService;
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
@RequestMapping("/inductionmentors")
public class InductionmentorController {

    private final InductionmentorService inductionmentorService;
    private final InductionmentorRepository inductionmentorRepository;
    private final InductionProgramRepository inductionProgramRepository;
    private final IMGroupsRepository iMGroupsRepository;

    public InductionmentorController(final InductionmentorService inductionmentorService,
            final InductionmentorRepository inductionmentorRepository,
            final InductionProgramRepository inductionProgramRepository,
            final IMGroupsRepository iMGroupsRepository) {
        this.inductionmentorService = inductionmentorService;
        this.inductionmentorRepository = inductionmentorRepository;
        this.inductionProgramRepository = inductionProgramRepository;
        this.iMGroupsRepository = iMGroupsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("iMmentoredbyNMValues", inductionmentorRepository.findAll().stream().collect(
                Collectors.toMap(Inductionmentor::getMentorID, Inductionmentor::getMentorID)));
        model.addAttribute("iMpartofInductionValues", inductionProgramRepository.findAll().stream().collect(
                Collectors.toMap(InductionProgram::getInductionId, InductionProgram::getDuration)));
        model.addAttribute("iMGrpcontainsIMValues", iMGroupsRepository.findAll().stream().collect(
                Collectors.toMap(IMGroups::getGroupID, IMGroups::getGroupID)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inductionmentors", inductionmentorService.findAll());
        return "inductionmentor/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("inductionmentor") final InductionmentorDTO inductionmentorDTO) {
        return "inductionmentor/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("inductionmentor") @Valid final InductionmentorDTO inductionmentorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionmentor/add";
        }
        inductionmentorService.create(inductionmentorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionmentor.create.success"));
        return "redirect:/inductionmentors";
    }

    @GetMapping("/edit/{mentorID}")
    public String edit(@PathVariable final Integer mentorID, final Model model) {
        model.addAttribute("inductionmentor", inductionmentorService.get(mentorID));
        return "inductionmentor/edit";
    }

    @PostMapping("/edit/{mentorID}")
    public String edit(@PathVariable final Integer mentorID,
            @ModelAttribute("inductionmentor") @Valid final InductionmentorDTO inductionmentorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inductionmentor/edit";
        }
        inductionmentorService.update(mentorID, inductionmentorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inductionmentor.update.success"));
        return "redirect:/inductionmentors";
    }

    @PostMapping("/delete/{mentorID}")
    public String delete(@PathVariable final Integer mentorID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inductionmentorService.getReferencedWarning(mentorID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inductionmentorService.delete(mentorID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inductionmentor.delete.success"));
        }
        return "redirect:/inductionmentors";
    }

}
