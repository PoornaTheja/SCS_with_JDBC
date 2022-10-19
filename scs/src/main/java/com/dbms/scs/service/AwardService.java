package com.dbms.scs.service;

import com.dbms.scs.domain.Award;
import com.dbms.scs.model.AwardDTO;
import com.dbms.scs.repos.AwardRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AwardService {

    private final AwardRepository awardRepository;

    public AwardService(final AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    public List<AwardDTO> findAll() {
        return awardRepository.findAll(Sort.by("awardID"))
                .stream()
                .map(award -> mapToDTO(award, new AwardDTO()))
                .collect(Collectors.toList());
    }

    public AwardDTO get(final Integer awardID) {
        return awardRepository.findById(awardID)
                .map(award -> mapToDTO(award, new AwardDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final AwardDTO awardDTO) {
        final Award award = new Award();
        mapToEntity(awardDTO, award);
        return awardRepository.save(award).getAwardID();
    }

    public void update(final Integer awardID, final AwardDTO awardDTO) {
        final Award award = awardRepository.findById(awardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(awardDTO, award);
        awardRepository.save(award);
    }

    public void delete(final Integer awardID) {
        awardRepository.deleteById(awardID);
    }

    private AwardDTO mapToDTO(final Award award, final AwardDTO awardDTO) {
        awardDTO.setAwardID(award.getAwardID());
        awardDTO.setAwardType(award.getAwardType());
        awardDTO.setCertificate(award.getCertificate());
        awardDTO.setYear(award.getYear());
        return awardDTO;
    }

    private Award mapToEntity(final AwardDTO awardDTO, final Award award) {
        award.setAwardType(awardDTO.getAwardType());
        award.setCertificate(awardDTO.getCertificate());
        award.setYear(awardDTO.getYear());
        return award;
    }

    @Transactional
    public String getReferencedWarning(final Integer awardID) {
        final Award award = awardRepository.findById(awardID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!award.getStudentawardedStudents().isEmpty()) {
            return WebUtils.getMessage("award.student.manyToMany.referenced", award.getStudentawardedStudents().iterator().next().getStudentRollNo());
        }
        return null;
    }

}
