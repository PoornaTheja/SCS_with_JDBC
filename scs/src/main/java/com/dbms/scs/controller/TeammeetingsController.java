package com.dbms.scs.controller;

import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.TeammeetingsDTO;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.service.TeammeetingsService;
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
@RequestMapping("/teammeetingss")
public class TeammeetingsController {

    private final TeammeetingsService teammeetingsService;
    private final VerticalRepository verticalRepository;

    public TeammeetingsController(final TeammeetingsService teammeetingsService,
            final VerticalRepository verticalRepository) {
        this.teammeetingsService = teammeetingsService;
        this.verticalRepository = verticalRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("meetbelongtoverticalValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("teammeetingss", teammeetingsService.findAll());
        return "teammeetings/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("teammeetings") final TeammeetingsDTO teammeetingsDTO) {
        return "teammeetings/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("teammeetings") @Valid final TeammeetingsDTO teammeetingsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "teammeetings/add";
        }
        teammeetingsService.create(teammeetingsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("teammeetings.create.success"));
        return "redirect:/teammeetingss";
    }

    @GetMapping("/edit/{meetingID}")
    public String edit(@PathVariable final Integer meetingID, final Model model) {
        model.addAttribute("teammeetings", teammeetingsService.get(meetingID));
        return "teammeetings/edit";
    }

    @PostMapping("/edit/{meetingID}")
    public String edit(@PathVariable final Integer meetingID,
            @ModelAttribute("teammeetings") @Valid final TeammeetingsDTO teammeetingsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "teammeetings/edit";
        }
        teammeetingsService.update(meetingID, teammeetingsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("teammeetings.update.success"));
        return "redirect:/teammeetingss";
    }

    @PostMapping("/delete/{meetingID}")
    public String delete(@PathVariable final Integer meetingID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = teammeetingsService.getReferencedWarning(meetingID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            teammeetingsService.delete(meetingID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("teammeetings.delete.success"));
        }
        return "redirect:/teammeetingss";
    }

}
