package com.dbms.scs.service;

import com.dbms.scs.domain.EventsPermission;
import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.EventsPermissionDTO;
import com.dbms.scs.repos.EventsPermissionRepository;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class EventsPermissionService {

    private final EventsPermissionRepository eventsPermissionRepository;
    private final FacultyRepository facultyRepository;
    private final SCSMembersRepository sCSMembersRepository;

    public EventsPermissionService(final EventsPermissionRepository eventsPermissionRepository,
            final FacultyRepository facultyRepository,
            final SCSMembersRepository sCSMembersRepository) {
        this.eventsPermissionRepository = eventsPermissionRepository;
        this.facultyRepository = facultyRepository;
        this.sCSMembersRepository = sCSMembersRepository;
    }

    public List<EventsPermissionDTO> findAll() {
        return eventsPermissionRepository.findAll(Sort.by("permissionID"))
                .stream()
                .map(eventsPermission -> mapToDTO(eventsPermission, new EventsPermissionDTO()))
                .collect(Collectors.toList());
    }

    public EventsPermissionDTO get(final Integer permissionID) {
        return eventsPermissionRepository.findById(permissionID)
                .map(eventsPermission -> mapToDTO(eventsPermission, new EventsPermissionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final EventsPermissionDTO eventsPermissionDTO) {
        final EventsPermission eventsPermission = new EventsPermission();
        mapToEntity(eventsPermissionDTO, eventsPermission);
        return eventsPermissionRepository.save(eventsPermission).getPermissionID();
    }

    public void update(final Integer permissionID, final EventsPermissionDTO eventsPermissionDTO) {
        final EventsPermission eventsPermission = eventsPermissionRepository.findById(permissionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(eventsPermissionDTO, eventsPermission);
        eventsPermissionRepository.save(eventsPermission);
    }

    public void delete(final Integer permissionID) {
        eventsPermissionRepository.deleteById(permissionID);
    }

    private EventsPermissionDTO mapToDTO(final EventsPermission eventsPermission,
            final EventsPermissionDTO eventsPermissionDTO) {
        eventsPermissionDTO.setPermissionID(eventsPermission.getPermissionID());
        eventsPermissionDTO.setAttestedBy(eventsPermission.getAttestedBy());
        eventsPermissionDTO.setGrantedBy(eventsPermission.getGrantedBy());
        eventsPermissionDTO.setPermissiongrantedby(eventsPermission.getPermissiongrantedby() == null ? null : eventsPermission.getPermissiongrantedby().getFacultyId());
        eventsPermissionDTO.setPermissionattestedby(eventsPermission.getPermissionattestedby() == null ? null : eventsPermission.getPermissionattestedby().getFacultyId());
        eventsPermissionDTO.setMembertakespermission(eventsPermission.getMembertakespermission() == null ? null : eventsPermission.getMembertakespermission().getMemberId());
        return eventsPermissionDTO;
    }

    private EventsPermission mapToEntity(final EventsPermissionDTO eventsPermissionDTO,
            final EventsPermission eventsPermission) {
        eventsPermission.setAttestedBy(eventsPermissionDTO.getAttestedBy());
        eventsPermission.setGrantedBy(eventsPermissionDTO.getGrantedBy());
        final Faculty permissiongrantedby = eventsPermissionDTO.getPermissiongrantedby() == null ? null : facultyRepository.findById(eventsPermissionDTO.getPermissiongrantedby())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "permissiongrantedby not found"));
        eventsPermission.setPermissiongrantedby(permissiongrantedby);
        final Faculty permissionattestedby = eventsPermissionDTO.getPermissionattestedby() == null ? null : facultyRepository.findById(eventsPermissionDTO.getPermissionattestedby())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "permissionattestedby not found"));
        eventsPermission.setPermissionattestedby(permissionattestedby);
        final SCSMembers membertakespermission = eventsPermissionDTO.getMembertakespermission() == null ? null : sCSMembersRepository.findById(eventsPermissionDTO.getMembertakespermission())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "membertakespermission not found"));
        eventsPermission.setMembertakespermission(membertakespermission);
        return eventsPermission;
    }

    @Transactional
    public String getReferencedWarning(final Integer permissionID) {
        final EventsPermission eventsPermission = eventsPermissionRepository.findById(permissionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (eventsPermission.getSeekpermission() != null) {
            return WebUtils.getMessage("eventsPermission.event.oneToOne.referenced", eventsPermission.getSeekpermission().getEventID());
        }
        return null;
    }

}
