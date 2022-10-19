package com.dbms.scs.service;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.InitiativesPermission;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.InitiativesPermissionDTO;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.InitiativesPermissionRepository;
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
public class InitiativesPermissionService {

    private final InitiativesPermissionRepository initiativesPermissionRepository;
    private final SCSMembersRepository sCSMembersRepository;
    private final FacultyRepository facultyRepository;

    public InitiativesPermissionService(
            final InitiativesPermissionRepository initiativesPermissionRepository,
            final SCSMembersRepository sCSMembersRepository,
            final FacultyRepository facultyRepository) {
        this.initiativesPermissionRepository = initiativesPermissionRepository;
        this.sCSMembersRepository = sCSMembersRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<InitiativesPermissionDTO> findAll() {
        return initiativesPermissionRepository.findAll(Sort.by("permissionID"))
                .stream()
                .map(initiativesPermission -> mapToDTO(initiativesPermission, new InitiativesPermissionDTO()))
                .collect(Collectors.toList());
    }

    public InitiativesPermissionDTO get(final Integer permissionID) {
        return initiativesPermissionRepository.findById(permissionID)
                .map(initiativesPermission -> mapToDTO(initiativesPermission, new InitiativesPermissionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final InitiativesPermissionDTO initiativesPermissionDTO) {
        final InitiativesPermission initiativesPermission = new InitiativesPermission();
        mapToEntity(initiativesPermissionDTO, initiativesPermission);
        return initiativesPermissionRepository.save(initiativesPermission).getPermissionID();
    }

    public void update(final Integer permissionID,
            final InitiativesPermissionDTO initiativesPermissionDTO) {
        final InitiativesPermission initiativesPermission = initiativesPermissionRepository.findById(permissionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(initiativesPermissionDTO, initiativesPermission);
        initiativesPermissionRepository.save(initiativesPermission);
    }

    public void delete(final Integer permissionID) {
        initiativesPermissionRepository.deleteById(permissionID);
    }

    private InitiativesPermissionDTO mapToDTO(final InitiativesPermission initiativesPermission,
            final InitiativesPermissionDTO initiativesPermissionDTO) {
        initiativesPermissionDTO.setPermissionID(initiativesPermission.getPermissionID());
        initiativesPermissionDTO.setAttestedBy(initiativesPermission.getAttestedBy());
        initiativesPermissionDTO.setGrantedBy(initiativesPermission.getGrantedBy());
        initiativesPermissionDTO.setMemberseekinitiativepermission(initiativesPermission.getMemberseekinitiativepermission() == null ? null : initiativesPermission.getMemberseekinitiativepermission().getMemberId());
        initiativesPermissionDTO.setInitpermissgranted(initiativesPermission.getInitpermissgranted() == null ? null : initiativesPermission.getInitpermissgranted().getFacultyId());
        initiativesPermissionDTO.setInitpermissattestedby(initiativesPermission.getInitpermissattestedby() == null ? null : initiativesPermission.getInitpermissattestedby().getFacultyId());
        return initiativesPermissionDTO;
    }

    private InitiativesPermission mapToEntity(
            final InitiativesPermissionDTO initiativesPermissionDTO,
            final InitiativesPermission initiativesPermission) {
        initiativesPermission.setAttestedBy(initiativesPermissionDTO.getAttestedBy());
        initiativesPermission.setGrantedBy(initiativesPermissionDTO.getGrantedBy());
        final SCSMembers memberseekinitiativepermission = initiativesPermissionDTO.getMemberseekinitiativepermission() == null ? null : sCSMembersRepository.findById(initiativesPermissionDTO.getMemberseekinitiativepermission())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "memberseekinitiativepermission not found"));
        initiativesPermission.setMemberseekinitiativepermission(memberseekinitiativepermission);
        final Faculty initpermissgranted = initiativesPermissionDTO.getInitpermissgranted() == null ? null : facultyRepository.findById(initiativesPermissionDTO.getInitpermissgranted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "initpermissgranted not found"));
        initiativesPermission.setInitpermissgranted(initpermissgranted);
        final Faculty initpermissattestedby = initiativesPermissionDTO.getInitpermissattestedby() == null ? null : facultyRepository.findById(initiativesPermissionDTO.getInitpermissattestedby())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "initpermissattestedby not found"));
        initiativesPermission.setInitpermissattestedby(initpermissattestedby);
        return initiativesPermission;
    }

    @Transactional
    public String getReferencedWarning(final Integer permissionID) {
        final InitiativesPermission initiativesPermission = initiativesPermissionRepository.findById(permissionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (initiativesPermission.getPermissionforinitiative() != null) {
            return WebUtils.getMessage("initiativesPermission.inititatives.oneToOne.referenced", initiativesPermission.getPermissionforinitiative().getInitiativesId());
        }
        return null;
    }

}
