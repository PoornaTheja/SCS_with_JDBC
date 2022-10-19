package com.dbms.scs.controller;

import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.FacultyDTO;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.service.FacultyService;
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
@RequestMapping("/facultys")
public class FacultyController {

    private final FacultyService facultyService;
    private final VerticalRepository verticalRepository;

    public FacultyController(final FacultyService facultyService,
            final VerticalRepository verticalRepository) {
        this.facultyService = facultyService;
        this.verticalRepository = verticalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("facultycounselsverticalssValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("facultys", facultyService.findAll());
        return "faculty/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("faculty") final FacultyDTO facultyDTO) {
        return "faculty/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("faculty") @Valid final FacultyDTO facultyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "faculty/add";
        }
        facultyService.create(facultyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("faculty.create.success"));
        return "redirect:/facultys";
    }

    @GetMapping("/edit/{facultyId}")
    public String edit(@PathVariable final Integer facultyId, final Model model) {
        model.addAttribute("faculty", facultyService.get(facultyId));
        return "faculty/edit";
    }

    @PostMapping("/edit/{facultyId}")
    public String edit(@PathVariable final Integer facultyId,
            @ModelAttribute("faculty") @Valid final FacultyDTO facultyDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "faculty/edit";
        }
        facultyService.update(facultyId, facultyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("faculty.update.success"));
        return "redirect:/facultys";
    }

    @PostMapping("/delete/{facultyId}")
    public String delete(@PathVariable final Integer facultyId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = facultyService.getReferencedWarning(facultyId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            facultyService.delete(facultyId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("faculty.delete.success"));
        }
        return "redirect:/facultys";
    }

}
