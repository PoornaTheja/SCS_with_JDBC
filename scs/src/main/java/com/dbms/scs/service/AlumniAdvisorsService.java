package com.dbms.scs.service;

import com.dbms.scs.domain.AlumniAdvisors;
import com.dbms.scs.model.AlumniAdvisorsDTO;
import com.dbms.scs.repos.AlumniAdvisorsRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AlumniAdvisorsService {

    private final AlumniAdvisorsRepository alumniAdvisorsRepository;

    public AlumniAdvisorsService(final AlumniAdvisorsRepository alumniAdvisorsRepository) {
        this.alumniAdvisorsRepository = alumniAdvisorsRepository;
    }

    public List<AlumniAdvisorsDTO> findAll() {
        return alumniAdvisorsRepository.findAll(Sort.by("alumniID"))
                .stream()
                .map(alumniAdvisors -> mapToDTO(alumniAdvisors, new AlumniAdvisorsDTO()))
                .collect(Collectors.toList());
    }

    public AlumniAdvisorsDTO get(final Integer alumniID) {
        return alumniAdvisorsRepository.findById(alumniID)
                .map(alumniAdvisors -> mapToDTO(alumniAdvisors, new AlumniAdvisorsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final AlumniAdvisorsDTO alumniAdvisorsDTO) {
        final AlumniAdvisors alumniAdvisors = new AlumniAdvisors();
        mapToEntity(alumniAdvisorsDTO, alumniAdvisors);
        return alumniAdvisorsRepository.save(alumniAdvisors).getAlumniID();
    }

    public void update(final Integer alumniID, final AlumniAdvisorsDTO alumniAdvisorsDTO) {
        final AlumniAdvisors alumniAdvisors = alumniAdvisorsRepository.findById(alumniID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(alumniAdvisorsDTO, alumniAdvisors);
        alumniAdvisorsRepository.save(alumniAdvisors);
    }

    public void delete(final Integer alumniID) {
        alumniAdvisorsRepository.deleteById(alumniID);
    }

    private AlumniAdvisorsDTO mapToDTO(final AlumniAdvisors alumniAdvisors,
            final AlumniAdvisorsDTO alumniAdvisorsDTO) {
        alumniAdvisorsDTO.setAlumniID(alumniAdvisors.getAlumniID());
        return alumniAdvisorsDTO;
    }

    private AlumniAdvisors mapToEntity(final AlumniAdvisorsDTO alumniAdvisorsDTO,
            final AlumniAdvisors alumniAdvisors) {
        return alumniAdvisors;
    }

    @Transactional
    public String getReferencedWarning(final Integer alumniID) {
        final AlumniAdvisors alumniAdvisors = alumniAdvisorsRepository.findById(alumniID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (alumniAdvisors.getStudentPassedOut() != null) {
            return WebUtils.getMessage("alumniAdvisors.student.oneToOne.referenced", alumniAdvisors.getStudentPassedOut().getStudentRollNo());
        }
        return null;
    }

}
