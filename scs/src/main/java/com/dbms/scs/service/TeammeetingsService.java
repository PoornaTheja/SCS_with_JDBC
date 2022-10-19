package com.dbms.scs.service;

import com.dbms.scs.domain.Teammeetings;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.TeammeetingsDTO;
import com.dbms.scs.repos.TeammeetingsRepository;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TeammeetingsService {

    private final TeammeetingsRepository teammeetingsRepository;
    private final VerticalRepository verticalRepository;

    public TeammeetingsService(final TeammeetingsRepository teammeetingsRepository,
            final VerticalRepository verticalRepository) {
        this.teammeetingsRepository = teammeetingsRepository;
        this.verticalRepository = verticalRepository;
    }

    public List<TeammeetingsDTO> findAll() {
        return teammeetingsRepository.findAll(Sort.by("meetingID"))
                .stream()
                .map(teammeetings -> mapToDTO(teammeetings, new TeammeetingsDTO()))
                .collect(Collectors.toList());
    }

    public TeammeetingsDTO get(final Integer meetingID) {
        return teammeetingsRepository.findById(meetingID)
                .map(teammeetings -> mapToDTO(teammeetings, new TeammeetingsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final TeammeetingsDTO teammeetingsDTO) {
        final Teammeetings teammeetings = new Teammeetings();
        mapToEntity(teammeetingsDTO, teammeetings);
        return teammeetingsRepository.save(teammeetings).getMeetingID();
    }

    public void update(final Integer meetingID, final TeammeetingsDTO teammeetingsDTO) {
        final Teammeetings teammeetings = teammeetingsRepository.findById(meetingID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(teammeetingsDTO, teammeetings);
        teammeetingsRepository.save(teammeetings);
    }

    public void delete(final Integer meetingID) {
        teammeetingsRepository.deleteById(meetingID);
    }

    private TeammeetingsDTO mapToDTO(final Teammeetings teammeetings,
            final TeammeetingsDTO teammeetingsDTO) {
        teammeetingsDTO.setMeetingID(teammeetings.getMeetingID());
        teammeetingsDTO.setNoOfAttendees(teammeetings.getNoOfAttendees());
        teammeetingsDTO.setKindOfMeeting(teammeetings.getKindOfMeeting());
        teammeetingsDTO.setDate(teammeetings.getDate());
        teammeetingsDTO.setFromTime(teammeetings.getFromTime());
        teammeetingsDTO.setToTime(teammeetings.getToTime());
        teammeetingsDTO.setLocation(teammeetings.getLocation());
        teammeetingsDTO.setResourcesLink(teammeetings.getResourcesLink());
        teammeetingsDTO.setMeetbelongtovertical(teammeetings.getMeetbelongtovertical() == null ? null : teammeetings.getMeetbelongtovertical().getVerticalID());
        return teammeetingsDTO;
    }

    private Teammeetings mapToEntity(final TeammeetingsDTO teammeetingsDTO,
            final Teammeetings teammeetings) {
        teammeetings.setNoOfAttendees(teammeetingsDTO.getNoOfAttendees());
        teammeetings.setKindOfMeeting(teammeetingsDTO.getKindOfMeeting());
        teammeetings.setDate(teammeetingsDTO.getDate());
        teammeetings.setFromTime(teammeetingsDTO.getFromTime());
        teammeetings.setToTime(teammeetingsDTO.getToTime());
        teammeetings.setLocation(teammeetingsDTO.getLocation());
        teammeetings.setResourcesLink(teammeetingsDTO.getResourcesLink());
        final Vertical meetbelongtovertical = teammeetingsDTO.getMeetbelongtovertical() == null ? null : verticalRepository.findById(teammeetingsDTO.getMeetbelongtovertical())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "meetbelongtovertical not found"));
        teammeetings.setMeetbelongtovertical(meetbelongtovertical);
        return teammeetings;
    }

    @Transactional
    public String getReferencedWarning(final Integer meetingID) {
        final Teammeetings teammeetings = teammeetingsRepository.findById(meetingID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!teammeetings.getMembersattendmeetSCSMemberss().isEmpty()) {
            return WebUtils.getMessage("teammeetings.sCSMembers.manyToMany.referenced", teammeetings.getMembersattendmeetSCSMemberss().iterator().next().getMemberId());
        }
        return null;
    }

}
