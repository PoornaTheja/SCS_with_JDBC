package com.dbms.scs.service;

import com.dbms.scs.domain.Bills;
import com.dbms.scs.domain.Inductionmentor;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.domain.Teammeetings;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.SCSMembersDTO;
import com.dbms.scs.repos.BillsRepository;
import com.dbms.scs.repos.InductionmentorRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.repos.TeammeetingsRepository;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.util.WebUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class SCSMembersService {

    private final SCSMembersRepository sCSMembersRepository;
    private final VerticalRepository verticalRepository;
    private final BillsRepository billsRepository;
    private final TeammeetingsRepository teammeetingsRepository;
    private final InductionmentorRepository inductionmentorRepository;

    public SCSMembersService(final SCSMembersRepository sCSMembersRepository,
            final VerticalRepository verticalRepository, final BillsRepository billsRepository,
            final TeammeetingsRepository teammeetingsRepository,
            final InductionmentorRepository inductionmentorRepository) {
        this.sCSMembersRepository = sCSMembersRepository;
        this.verticalRepository = verticalRepository;
        this.billsRepository = billsRepository;
        this.teammeetingsRepository = teammeetingsRepository;
        this.inductionmentorRepository = inductionmentorRepository;
    }

    public List<SCSMembersDTO> findAll() {
        return sCSMembersRepository.findAll(Sort.by("memberId"))
                .stream()
                .map(sCSMembers -> mapToDTO(sCSMembers, new SCSMembersDTO()))
                .collect(Collectors.toList());
    }

    public SCSMembersDTO get(final Integer memberId) {
        return sCSMembersRepository.findById(memberId)
                .map(sCSMembers -> mapToDTO(sCSMembers, new SCSMembersDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final SCSMembersDTO sCSMembersDTO) {
        final SCSMembers sCSMembers = new SCSMembers();
        mapToEntity(sCSMembersDTO, sCSMembers);
        return sCSMembersRepository.save(sCSMembers).getMemberId();
    }

    public void update(final Integer memberId, final SCSMembersDTO sCSMembersDTO) {
        final SCSMembers sCSMembers = sCSMembersRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(sCSMembersDTO, sCSMembers);
        sCSMembersRepository.save(sCSMembers);
    }

    public void delete(final Integer memberId) {
        sCSMembersRepository.deleteById(memberId);
    }

    private SCSMembersDTO mapToDTO(final SCSMembers sCSMembers, final SCSMembersDTO sCSMembersDTO) {
        sCSMembersDTO.setMemberId(sCSMembers.getMemberId());
        sCSMembersDTO.setFromDate(sCSMembers.getFromDate());
        sCSMembersDTO.setToDate(sCSMembers.getToDate());
        sCSMembersDTO.setCurrentPosition(sCSMembers.getCurrentPosition());
        sCSMembersDTO.setVerticalmemberbelongs(sCSMembers.getVerticalmemberbelongs() == null ? null : sCSMembers.getVerticalmemberbelongs().getVerticalID());
        sCSMembersDTO.setMembersresponsblebills(sCSMembers.getMembersresponsblebills() == null ? null : sCSMembers.getMembersresponsblebills().getBillID());
        sCSMembersDTO.setMembersattendmeets(sCSMembers.getMembersattendmeetTeammeetingss() == null ? null : sCSMembers.getMembersattendmeetTeammeetingss().stream()
                .map(teammeetings -> teammeetings.getMeetingID())
                .collect(Collectors.toList()));
        sCSMembersDTO.setMemberbecomesIM(sCSMembers.getMemberbecomesIM() == null ? null : sCSMembers.getMemberbecomesIM().getMentorID());
        return sCSMembersDTO;
    }

    private SCSMembers mapToEntity(final SCSMembersDTO sCSMembersDTO, final SCSMembers sCSMembers) {
        sCSMembers.setFromDate(sCSMembersDTO.getFromDate());
        sCSMembers.setToDate(sCSMembersDTO.getToDate());
        sCSMembers.setCurrentPosition(sCSMembersDTO.getCurrentPosition());
        final Vertical verticalmemberbelongs = sCSMembersDTO.getVerticalmemberbelongs() == null ? null : verticalRepository.findById(sCSMembersDTO.getVerticalmemberbelongs())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "verticalmemberbelongs not found"));
        sCSMembers.setVerticalmemberbelongs(verticalmemberbelongs);
        final Bills membersresponsblebills = sCSMembersDTO.getMembersresponsblebills() == null ? null : billsRepository.findById(sCSMembersDTO.getMembersresponsblebills())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "membersresponsblebills not found"));
        sCSMembers.setMembersresponsblebills(membersresponsblebills);
        final List<Teammeetings> membersattendmeets = teammeetingsRepository.findAllById(
                sCSMembersDTO.getMembersattendmeets() == null ? Collections.emptyList() : sCSMembersDTO.getMembersattendmeets());
        if (membersattendmeets.size() != (sCSMembersDTO.getMembersattendmeets() == null ? 0 : sCSMembersDTO.getMembersattendmeets().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of membersattendmeets not found");
        }
        sCSMembers.setMembersattendmeetTeammeetingss(membersattendmeets.stream().collect(Collectors.toSet()));
        final Inductionmentor memberbecomesIM = sCSMembersDTO.getMemberbecomesIM() == null ? null : inductionmentorRepository.findById(sCSMembersDTO.getMemberbecomesIM())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "memberbecomesIM not found"));
        sCSMembers.setMemberbecomesIM(memberbecomesIM);
        return sCSMembers;
    }

    @Transactional
    public String getReferencedWarning(final Integer memberId) {
        final SCSMembers sCSMembers = sCSMembersRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!sCSMembers.getHonorarirumResponsiblesHonorariums().isEmpty()) {
            return WebUtils.getMessage("sCSMembers.honorarium.oneToMany.referenced", sCSMembers.getHonorarirumResponsiblesHonorariums().iterator().next().getHonorariumID());
        } else if (sCSMembers.getSCSMembership() != null) {
            return WebUtils.getMessage("sCSMembers.student.oneToOne.referenced", sCSMembers.getSCSMembership().getStudentRollNo());
        } else if (!sCSMembers.getMembertakespermissionEventsPermissions().isEmpty()) {
            return WebUtils.getMessage("sCSMembers.eventsPermission.oneToMany.referenced", sCSMembers.getMembertakespermissionEventsPermissions().iterator().next().getPermissionID());
        } else if (!sCSMembers.getMemberseekinitiativepermissionInitiativesPermissions().isEmpty()) {
            return WebUtils.getMessage("sCSMembers.initiativesPermission.oneToMany.referenced", sCSMembers.getMemberseekinitiativepermissionInitiativesPermissions().iterator().next().getPermissionID());
        } else if (sCSMembers.getApplicantbecomesmember() != null) {
            return WebUtils.getMessage("sCSMembers.application.oneToOne.referenced", sCSMembers.getApplicantbecomesmember().getApplicationID());
        }
        return null;
    }

}
