package com.dbms.scs.controller;

import com.dbms.scs.domain.Bills;
import com.dbms.scs.domain.Inductionmentor;
import com.dbms.scs.domain.Teammeetings;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.SCSMembersDTO;
import com.dbms.scs.repos.BillsRepository;
import com.dbms.scs.repos.InductionmentorRepository;
import com.dbms.scs.repos.TeammeetingsRepository;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.service.SCSMembersService;
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
@RequestMapping("/sCSMemberss")
public class SCSMembersController {

    private final SCSMembersService sCSMembersService;
    private final VerticalRepository verticalRepository;
    private final BillsRepository billsRepository;
    private final TeammeetingsRepository teammeetingsRepository;
    private final InductionmentorRepository inductionmentorRepository;

    public SCSMembersController(final SCSMembersService sCSMembersService,
            final VerticalRepository verticalRepository, final BillsRepository billsRepository,
            final TeammeetingsRepository teammeetingsRepository,
            final InductionmentorRepository inductionmentorRepository) {
        this.sCSMembersService = sCSMembersService;
        this.verticalRepository = verticalRepository;
        this.billsRepository = billsRepository;
        this.teammeetingsRepository = teammeetingsRepository;
        this.inductionmentorRepository = inductionmentorRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("verticalmemberbelongsValues", verticalRepository.findAll().stream().collect(
                Collectors.toMap(Vertical::getVerticalID, Vertical::getNameOfVertical)));
        model.addAttribute("membersresponsblebillsValues", billsRepository.findAll().stream().collect(
                Collectors.toMap(Bills::getBillID, Bills::getPurpose)));
        model.addAttribute("membersattendmeetsValues", teammeetingsRepository.findAll().stream().collect(
                Collectors.toMap(Teammeetings::getMeetingID, Teammeetings::getKindOfMeeting)));
        model.addAttribute("memberbecomesIMValues", inductionmentorRepository.findAll().stream().collect(
                Collectors.toMap(Inductionmentor::getMentorID, Inductionmentor::getMentorID)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sCSMemberss", sCSMembersService.findAll());
        return "sCSMembers/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sCSMembers") final SCSMembersDTO sCSMembersDTO) {
        return "sCSMembers/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sCSMembers") @Valid final SCSMembersDTO sCSMembersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sCSMembers/add";
        }
        sCSMembersService.create(sCSMembersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sCSMembers.create.success"));
        return "redirect:/sCSMemberss";
    }

    @GetMapping("/edit/{memberId}")
    public String edit(@PathVariable final Integer memberId, final Model model) {
        model.addAttribute("sCSMembers", sCSMembersService.get(memberId));
        return "sCSMembers/edit";
    }

    @PostMapping("/edit/{memberId}")
    public String edit(@PathVariable final Integer memberId,
            @ModelAttribute("sCSMembers") @Valid final SCSMembersDTO sCSMembersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sCSMembers/edit";
        }
        sCSMembersService.update(memberId, sCSMembersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sCSMembers.update.success"));
        return "redirect:/sCSMemberss";
    }

    @PostMapping("/delete/{memberId}")
    public String delete(@PathVariable final Integer memberId,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = sCSMembersService.getReferencedWarning(memberId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            sCSMembersService.delete(memberId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sCSMembers.delete.success"));
        }
        return "redirect:/sCSMemberss";
    }

}
