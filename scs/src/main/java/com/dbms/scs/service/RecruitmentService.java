package com.dbms.scs.service;

import com.dbms.scs.domain.Recruitment;
import com.dbms.scs.model.RecruitmentDTO;
import com.dbms.scs.repos.RecruitmentRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    public RecruitmentService(final RecruitmentRepository recruitmentRepository) {
        this.recruitmentRepository = recruitmentRepository;
    }

    public List<RecruitmentDTO> findAll() {
        return recruitmentRepository.findAll(Sort.by("recruitmentID"))
                .stream()
                .map(recruitment -> mapToDTO(recruitment, new RecruitmentDTO()))
                .collect(Collectors.toList());
    }

    public RecruitmentDTO get(final Integer recruitmentID) {
        return recruitmentRepository.findById(recruitmentID)
                .map(recruitment -> mapToDTO(recruitment, new RecruitmentDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final RecruitmentDTO recruitmentDTO) {
        final Recruitment recruitment = new Recruitment();
        mapToEntity(recruitmentDTO, recruitment);
        return recruitmentRepository.save(recruitment).getRecruitmentID();
    }

    public void update(final Integer recruitmentID, final RecruitmentDTO recruitmentDTO) {
        final Recruitment recruitment = recruitmentRepository.findById(recruitmentID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(recruitmentDTO, recruitment);
        recruitmentRepository.save(recruitment);
    }

    public void delete(final Integer recruitmentID) {
        recruitmentRepository.deleteById(recruitmentID);
    }

    private RecruitmentDTO mapToDTO(final Recruitment recruitment,
            final RecruitmentDTO recruitmentDTO) {
        recruitmentDTO.setRecruitmentID(recruitment.getRecruitmentID());
        recruitmentDTO.setYear(recruitment.getYear());
        recruitmentDTO.setPOR(recruitment.getPOR());
        return recruitmentDTO;
    }

    private Recruitment mapToEntity(final RecruitmentDTO recruitmentDTO,
            final Recruitment recruitment) {
        recruitment.setYear(recruitmentDTO.getYear());
        recruitment.setPOR(recruitmentDTO.getPOR());
        return recruitment;
    }

    @Transactional
    public String getReferencedWarning(final Integer recruitmentID) {
        final Recruitment recruitment = recruitmentRepository.findById(recruitmentID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!recruitment.getApplicnforrecruitmentApplications().isEmpty()) {
            return WebUtils.getMessage("recruitment.application.manyToOne.referenced", recruitment.getApplicnforrecruitmentApplications().iterator().next().getApplicationID());
        }
        return null;
    }

}
