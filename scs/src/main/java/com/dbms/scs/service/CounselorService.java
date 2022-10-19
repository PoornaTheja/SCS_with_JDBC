package com.dbms.scs.service;

import com.dbms.scs.domain.Counselor;
import com.dbms.scs.model.CounselorDTO;
import com.dbms.scs.repos.CounselorRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CounselorService {

    private final CounselorRepository counselorRepository;

    public CounselorService(final CounselorRepository counselorRepository) {
        this.counselorRepository = counselorRepository;
    }

    public List<CounselorDTO> findAll() {
        return counselorRepository.findAll(Sort.by("counselorId"))
                .stream()
                .map(counselor -> mapToDTO(counselor, new CounselorDTO()))
                .collect(Collectors.toList());
    }

    public CounselorDTO get(final Integer counselorId) {
        return counselorRepository.findById(counselorId)
                .map(counselor -> mapToDTO(counselor, new CounselorDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final CounselorDTO counselorDTO) {
        final Counselor counselor = new Counselor();
        mapToEntity(counselorDTO, counselor);
        return counselorRepository.save(counselor).getCounselorId();
    }

    public void update(final Integer counselorId, final CounselorDTO counselorDTO) {
        final Counselor counselor = counselorRepository.findById(counselorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(counselorDTO, counselor);
        counselorRepository.save(counselor);
    }

    public void delete(final Integer counselorId) {
        counselorRepository.deleteById(counselorId);
    }

    private CounselorDTO mapToDTO(final Counselor counselor, final CounselorDTO counselorDTO) {
        counselorDTO.setCounselorId(counselor.getCounselorId());
        counselorDTO.setName(counselor.getName());
        counselorDTO.setQualification(counselor.getQualification());
        counselorDTO.setDesignation(counselor.getDesignation());
        counselorDTO.setDateOfJoining(counselor.getDateOfJoining());
        counselorDTO.setTimings(counselor.getTimings());
        counselorDTO.setJobType(counselor.getJobType());
        counselorDTO.setPhoneNo(counselor.getPhoneNo());
        counselorDTO.setEmailId(counselor.getEmailId());
        counselorDTO.setCurrentStatus(counselor.getCurrentStatus());
        return counselorDTO;
    }

    private Counselor mapToEntity(final CounselorDTO counselorDTO, final Counselor counselor) {
        counselor.setName(counselorDTO.getName());
        counselor.setQualification(counselorDTO.getQualification());
        counselor.setDesignation(counselorDTO.getDesignation());
        counselor.setDateOfJoining(counselorDTO.getDateOfJoining());
        counselor.setTimings(counselorDTO.getTimings());
        counselor.setJobType(counselorDTO.getJobType());
        counselor.setPhoneNo(counselorDTO.getPhoneNo());
        counselor.setEmailId(counselorDTO.getEmailId());
        counselor.setCurrentStatus(counselorDTO.getCurrentStatus());
        return counselor;
    }

    @Transactional
    public String getReferencedWarning(final Integer counselorId) {
        final Counselor counselor = counselorRepository.findById(counselorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!counselor.getCounseledByCounsellingSessionss().isEmpty()) {
            return WebUtils.getMessage("counselor.counsellingSessions.manyToOne.referenced", counselor.getCounseledByCounsellingSessionss().iterator().next().getSessionID());
        }
        return null;
    }

}
