package com.dbms.scs.service;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.FacultyDTO;
import com.dbms.scs.repos.FacultyRepository;
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
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final VerticalRepository verticalRepository;

    public FacultyService(final FacultyRepository facultyRepository,
            final VerticalRepository verticalRepository) {
        this.facultyRepository = facultyRepository;
        this.verticalRepository = verticalRepository;
    }

    public List<FacultyDTO> findAll() {
        return facultyRepository.findAll(Sort.by("facultyId"))
                .stream()
                .map(faculty -> mapToDTO(faculty, new FacultyDTO()))
                .collect(Collectors.toList());
    }

    public FacultyDTO get(final Integer facultyId) {
        return facultyRepository.findById(facultyId)
                .map(faculty -> mapToDTO(faculty, new FacultyDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final FacultyDTO facultyDTO) {
        final Faculty faculty = new Faculty();
        mapToEntity(facultyDTO, faculty);
        return facultyRepository.save(faculty).getFacultyId();
    }

    public void update(final Integer facultyId, final FacultyDTO facultyDTO) {
        final Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(facultyDTO, faculty);
        facultyRepository.save(faculty);
    }

    public void delete(final Integer facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    private FacultyDTO mapToDTO(final Faculty faculty, final FacultyDTO facultyDTO) {
        facultyDTO.setFacultyId(faculty.getFacultyId());
        facultyDTO.setDepartment(faculty.getDepartment());
        facultyDTO.setPhoneNo(faculty.getPhoneNo());
        facultyDTO.setEmailId(faculty.getEmailId());
        facultyDTO.setQualification(faculty.getQualification());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setFacultycounselsverticalss(faculty.getFacultycounselsverticalsVerticals() == null ? null : faculty.getFacultycounselsverticalsVerticals().stream()
                .map(vertical -> vertical.getVerticalID())
                .collect(Collectors.toList()));
        return facultyDTO;
    }

    private Faculty mapToEntity(final FacultyDTO facultyDTO, final Faculty faculty) {
        faculty.setDepartment(facultyDTO.getDepartment());
        faculty.setPhoneNo(facultyDTO.getPhoneNo());
        faculty.setEmailId(facultyDTO.getEmailId());
        faculty.setQualification(facultyDTO.getQualification());
        faculty.setName(facultyDTO.getName());
        final List<Vertical> facultycounselsverticalss = verticalRepository.findAllById(
                facultyDTO.getFacultycounselsverticalss() == null ? Collections.emptyList() : facultyDTO.getFacultycounselsverticalss());
        if (facultycounselsverticalss.size() != (facultyDTO.getFacultycounselsverticalss() == null ? 0 : facultyDTO.getFacultycounselsverticalss().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of facultycounselsverticalss not found");
        }
        faculty.setFacultycounselsverticalsVerticals(facultycounselsverticalss.stream().collect(Collectors.toSet()));
        return faculty;
    }

    @Transactional
    public String getReferencedWarning(final Integer facultyId) {
        final Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!faculty.getPermissiongrantedbyEventsPermissions().isEmpty()) {
            return WebUtils.getMessage("faculty.eventsPermission.oneToMany.referenced", faculty.getPermissiongrantedbyEventsPermissions().iterator().next().getPermissionID());
        } else if (!faculty.getPermissionattestedbyEventsPermissions().isEmpty()) {
            return WebUtils.getMessage("faculty.eventsPermission.oneToMany.referenced", faculty.getPermissionattestedbyEventsPermissions().iterator().next().getPermissionID());
        } else if (!faculty.getInitpermissgrantedInitiativesPermissions().isEmpty()) {
            return WebUtils.getMessage("faculty.initiativesPermission.manyToOne.referenced", faculty.getInitpermissgrantedInitiativesPermissions().iterator().next().getPermissionID());
        } else if (!faculty.getInitpermissattestedbyInitiativesPermissions().isEmpty()) {
            return WebUtils.getMessage("faculty.initiativesPermission.manyToOne.referenced", faculty.getInitpermissattestedbyInitiativesPermissions().iterator().next().getPermissionID());
        } else if (faculty.getIMgrpssupervisedbyFac() != null) {
            return WebUtils.getMessage("faculty.iMGroups.oneToOne.referenced", faculty.getIMgrpssupervisedbyFac().getGroupID());
        }
        return null;
    }

}
