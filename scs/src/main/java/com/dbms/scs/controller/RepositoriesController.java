package com.dbms.scs.controller;

import com.dbms.scs.domain.Student;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.RepositoriesDTO;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.service.RepositoriesService;
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
@RequestMapping("/repositoriess")
public class RepositoriesController {

    private final RepositoriesService repositoriesService;
    private final StudentRepository studentRepository;
    private final VerticalRepository verticalRepository;

    public RepositoriesController(final RepositoriesService repositoriesService,
            final StudentRepository studentRepository,
            final VerticalRepository verticalRepository) {
        this.repositoriesService = repositoriesService;
        this.studentRepository = studentRepository;
        this.verticalRepository = verticalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("studentCompilesreposValues", studentRepository.findAll().stream().collect(
                Collectors.toMap(Student::getStudentRollNo, Student::getName)));
        model.addAttribute("verticalReposValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("repositoriess", repositoriesService.findAll());
        return "repositories/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("repositories") final RepositoriesDTO repositoriesDTO) {
        return "repositories/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("repositories") @Valid final RepositoriesDTO repositoriesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "repositories/add";
        }
        repositoriesService.create(repositoriesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("repositories.create.success"));
        return "redirect:/repositoriess";
    }

    @GetMapping("/edit/{repoID}")
    public String edit(@PathVariable final Integer repoID, final Model model) {
        model.addAttribute("repositories", repositoriesService.get(repoID));
        return "repositories/edit";
    }

    @PostMapping("/edit/{repoID}")
    public String edit(@PathVariable final Integer repoID,
            @ModelAttribute("repositories") @Valid final RepositoriesDTO repositoriesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "repositories/edit";
        }
        repositoriesService.update(repoID, repositoriesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("repositories.update.success"));
        return "redirect:/repositoriess";
    }

    @PostMapping("/delete/{repoID}")
    public String delete(@PathVariable final Integer repoID,
            final RedirectAttributes redirectAttributes) {
        repositoriesService.delete(repoID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("repositories.delete.success"));
        return "redirect:/repositoriess";
    }

}
