package com.dbms.scs.service;

import com.dbms.scs.domain.InitiativesPermission;
import com.dbms.scs.domain.Inititatives;
import com.dbms.scs.domain.Venue;
import com.dbms.scs.model.InititativesDTO;
import com.dbms.scs.repos.InitiativesPermissionRepository;
import com.dbms.scs.repos.InititativesRepository;
import com.dbms.scs.repos.VenueRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class InititativesService {

    private final InititativesRepository inititativesRepository;
    private final InitiativesPermissionRepository initiativesPermissionRepository;
    private final VenueRepository venueRepository;

    public InititativesService(final InititativesRepository inititativesRepository,
            final InitiativesPermissionRepository initiativesPermissionRepository,
            final VenueRepository venueRepository) {
        this.inititativesRepository = inititativesRepository;
        this.initiativesPermissionRepository = initiativesPermissionRepository;
        this.venueRepository = venueRepository;
    }

    public List<InititativesDTO> findAll() {
        return inititativesRepository.findAll(Sort.by("initiativesId"))
                .stream()
                .map(inititatives -> mapToDTO(inititatives, new InititativesDTO()))
                .collect(Collectors.toList());
    }

    public InititativesDTO get(final Integer initiativesId) {
        return inititativesRepository.findById(initiativesId)
                .map(inititatives -> mapToDTO(inititatives, new InititativesDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final InititativesDTO inititativesDTO) {
        final Inititatives inititatives = new Inititatives();
        mapToEntity(inititativesDTO, inititatives);
        return inititativesRepository.save(inititatives).getInitiativesId();
    }

    public void update(final Integer initiativesId, final InititativesDTO inititativesDTO) {
        final Inititatives inititatives = inititativesRepository.findById(initiativesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(inititativesDTO, inititatives);
        inititativesRepository.save(inititatives);
    }

    public void delete(final Integer initiativesId) {
        inititativesRepository.deleteById(initiativesId);
    }

    private InititativesDTO mapToDTO(final Inititatives inititatives,
            final InititativesDTO inititativesDTO) {
        inititativesDTO.setInitiativesId(inititatives.getInitiativesId());
        inititativesDTO.setTitle(inititatives.getTitle());
        inititativesDTO.setStartDate(inititatives.getStartDate());
        inititativesDTO.setTimings(inititatives.getTimings());
        inititativesDTO.setNoOfAttendees(inititatives.getNoOfAttendees());
        inititativesDTO.setResources(inititatives.getResources());
        inititativesDTO.setMinutesOfMeeting(inititatives.getMinutesOfMeeting());
        inititativesDTO.setPermissionforinitiative(inititatives.getPermissionforinitiative() == null ? null : inititatives.getPermissionforinitiative().getPermissionID());
        inititativesDTO.setInitiatvehostedat(inititatives.getInitiatvehostedat() == null ? null : inititatives.getInitiatvehostedat().getVenueID());
        return inititativesDTO;
    }

    private Inititatives mapToEntity(final InititativesDTO inititativesDTO,
            final Inititatives inititatives) {
        inititatives.setTitle(inititativesDTO.getTitle());
        inititatives.setStartDate(inititativesDTO.getStartDate());
        inititatives.setTimings(inititativesDTO.getTimings());
        inititatives.setNoOfAttendees(inititativesDTO.getNoOfAttendees());
        inititatives.setResources(inititativesDTO.getResources());
        inititatives.setMinutesOfMeeting(inititativesDTO.getMinutesOfMeeting());
        final InitiativesPermission permissionforinitiative = inititativesDTO.getPermissionforinitiative() == null ? null : initiativesPermissionRepository.findById(inititativesDTO.getPermissionforinitiative())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "permissionforinitiative not found"));
        inititatives.setPermissionforinitiative(permissionforinitiative);
        final Venue initiatvehostedat = inititativesDTO.getInitiatvehostedat() == null ? null : venueRepository.findById(inititativesDTO.getInitiatvehostedat())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "initiatvehostedat not found"));
        inititatives.setInitiatvehostedat(initiatvehostedat);
        return inititatives;
    }

    @Transactional
    public String getReferencedWarning(final Integer initiativesId) {
        final Inititatives inititatives = inititativesRepository.findById(initiativesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!inititatives.getStudinitiativepartStudents().isEmpty()) {
            return WebUtils.getMessage("inititatives.student.manyToMany.referenced", inititatives.getStudinitiativepartStudents().iterator().next().getStudentRollNo());
        }
        return null;
    }

}
