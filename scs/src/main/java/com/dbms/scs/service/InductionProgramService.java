package com.dbms.scs.service;

import com.dbms.scs.domain.InductionProgram;
import com.dbms.scs.model.InductionProgramDTO;
import com.dbms.scs.repos.InductionProgramRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class InductionProgramService {

    private final InductionProgramRepository inductionProgramRepository;

    public InductionProgramService(final InductionProgramRepository inductionProgramRepository) {
        this.inductionProgramRepository = inductionProgramRepository;
    }

    public List<InductionProgramDTO> findAll() {
        return inductionProgramRepository.findAll(Sort.by("inductionId"))
                .stream()
                .map(inductionProgram -> mapToDTO(inductionProgram, new InductionProgramDTO()))
                .collect(Collectors.toList());
    }

    public InductionProgramDTO get(final Integer inductionId) {
        return inductionProgramRepository.findById(inductionId)
                .map(inductionProgram -> mapToDTO(inductionProgram, new InductionProgramDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final InductionProgramDTO inductionProgramDTO) {
        final InductionProgram inductionProgram = new InductionProgram();
        mapToEntity(inductionProgramDTO, inductionProgram);
        return inductionProgramRepository.save(inductionProgram).getInductionId();
    }

    public void update(final Integer inductionId, final InductionProgramDTO inductionProgramDTO) {
        final InductionProgram inductionProgram = inductionProgramRepository.findById(inductionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(inductionProgramDTO, inductionProgram);
        inductionProgramRepository.save(inductionProgram);
    }

    public void delete(final Integer inductionId) {
        inductionProgramRepository.deleteById(inductionId);
    }

    private InductionProgramDTO mapToDTO(final InductionProgram inductionProgram,
            final InductionProgramDTO inductionProgramDTO) {
        inductionProgramDTO.setInductionId(inductionProgram.getInductionId());
        inductionProgramDTO.setYear(inductionProgram.getYear());
        inductionProgramDTO.setDuration(inductionProgram.getDuration());
        inductionProgramDTO.setChairman(inductionProgram.getChairman());
        inductionProgramDTO.setNoOfAttendees(inductionProgram.getNoOfAttendees());
        return inductionProgramDTO;
    }

    private InductionProgram mapToEntity(final InductionProgramDTO inductionProgramDTO,
            final InductionProgram inductionProgram) {
        inductionProgram.setYear(inductionProgramDTO.getYear());
        inductionProgram.setDuration(inductionProgramDTO.getDuration());
        inductionProgram.setChairman(inductionProgramDTO.getChairman());
        inductionProgram.setNoOfAttendees(inductionProgramDTO.getNoOfAttendees());
        return inductionProgram;
    }

    @Transactional
    public String getReferencedWarning(final Integer inductionId) {
        final InductionProgram inductionProgram = inductionProgramRepository.findById(inductionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!inductionProgram.getIMpartofInductionInductionmentors().isEmpty()) {
            return WebUtils.getMessage("inductionProgram.inductionmentor.manyToOne.referenced", inductionProgram.getIMpartofInductionInductionmentors().iterator().next().getMentorID());
        } else if (!inductionProgram.getIMgroupsinprogramIMGroupss().isEmpty()) {
            return WebUtils.getMessage("inductionProgram.iMGroups.oneToMany.referenced", inductionProgram.getIMgroupsinprogramIMGroupss().iterator().next().getGroupID());
        }
        return null;
    }

}
