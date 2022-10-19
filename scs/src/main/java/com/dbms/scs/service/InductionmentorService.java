package com.dbms.scs.service;

import com.dbms.scs.domain.IMGroups;
import com.dbms.scs.domain.InductionProgram;
import com.dbms.scs.domain.Inductionmentor;
import com.dbms.scs.model.InductionmentorDTO;
import com.dbms.scs.repos.IMGroupsRepository;
import com.dbms.scs.repos.InductionProgramRepository;
import com.dbms.scs.repos.InductionmentorRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class InductionmentorService {

    private final InductionmentorRepository inductionmentorRepository;
    private final InductionProgramRepository inductionProgramRepository;
    private final IMGroupsRepository iMGroupsRepository;

    public InductionmentorService(final InductionmentorRepository inductionmentorRepository,
            final InductionProgramRepository inductionProgramRepository,
            final IMGroupsRepository iMGroupsRepository) {
        this.inductionmentorRepository = inductionmentorRepository;
        this.inductionProgramRepository = inductionProgramRepository;
        this.iMGroupsRepository = iMGroupsRepository;
    }

    public List<InductionmentorDTO> findAll() {
        return inductionmentorRepository.findAll(Sort.by("mentorID"))
                .stream()
                .map(inductionmentor -> mapToDTO(inductionmentor, new InductionmentorDTO()))
                .collect(Collectors.toList());
    }

    public InductionmentorDTO get(final Integer mentorID) {
        return inductionmentorRepository.findById(mentorID)
                .map(inductionmentor -> mapToDTO(inductionmentor, new InductionmentorDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final InductionmentorDTO inductionmentorDTO) {
        final Inductionmentor inductionmentor = new Inductionmentor();
        mapToEntity(inductionmentorDTO, inductionmentor);
        return inductionmentorRepository.save(inductionmentor).getMentorID();
    }

    public void update(final Integer mentorID, final InductionmentorDTO inductionmentorDTO) {
        final Inductionmentor inductionmentor = inductionmentorRepository.findById(mentorID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(inductionmentorDTO, inductionmentor);
        inductionmentorRepository.save(inductionmentor);
    }

    public void delete(final Integer mentorID) {
        inductionmentorRepository.deleteById(mentorID);
    }

    private InductionmentorDTO mapToDTO(final Inductionmentor inductionmentor,
            final InductionmentorDTO inductionmentorDTO) {
        inductionmentorDTO.setMentorID(inductionmentor.getMentorID());
        inductionmentorDTO.setNodalMentorID(inductionmentor.getNodalMentorID());
        inductionmentorDTO.setIMmentoredbyNM(inductionmentor.getIMmentoredbyNM() == null ? null : inductionmentor.getIMmentoredbyNM().getMentorID());
        inductionmentorDTO.setIMpartofInduction(inductionmentor.getIMpartofInduction() == null ? null : inductionmentor.getIMpartofInduction().getInductionId());
        inductionmentorDTO.setIMGrpcontainsIM(inductionmentor.getIMGrpcontainsIM() == null ? null : inductionmentor.getIMGrpcontainsIM().getGroupID());
        return inductionmentorDTO;
    }

    private Inductionmentor mapToEntity(final InductionmentorDTO inductionmentorDTO,
            final Inductionmentor inductionmentor) {
        inductionmentor.setNodalMentorID(inductionmentorDTO.getNodalMentorID());
        final Inductionmentor iMmentoredbyNM = inductionmentorDTO.getIMmentoredbyNM() == null ? null : inductionmentorRepository.findById(inductionmentorDTO.getIMmentoredbyNM())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "iMmentoredbyNM not found"));
        inductionmentor.setIMmentoredbyNM(iMmentoredbyNM);
        final InductionProgram iMpartofInduction = inductionmentorDTO.getIMpartofInduction() == null ? null : inductionProgramRepository.findById(inductionmentorDTO.getIMpartofInduction())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "iMpartofInduction not found"));
        inductionmentor.setIMpartofInduction(iMpartofInduction);
        final IMGroups iMGrpcontainsIM = inductionmentorDTO.getIMGrpcontainsIM() == null ? null : iMGroupsRepository.findById(inductionmentorDTO.getIMGrpcontainsIM())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "iMGrpcontainsIM not found"));
        inductionmentor.setIMGrpcontainsIM(iMGrpcontainsIM);
        return inductionmentor;
    }

    @Transactional
    public String getReferencedWarning(final Integer mentorID) {
        final Inductionmentor inductionmentor = inductionmentorRepository.findById(mentorID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (inductionmentor.getMemberbecomesIM() != null) {
            return WebUtils.getMessage("inductionmentor.sCSMembers.oneToOne.referenced", inductionmentor.getMemberbecomesIM().getMemberId());
        } else if (!inductionmentor.getIMmentoredbyNMInductionmentors().isEmpty()) {
            return WebUtils.getMessage("inductionmentor.inductionmentor.manyToOne.referenced", inductionmentor.getIMmentoredbyNMInductionmentors().iterator().next().getMentorID());
        }
        return null;
    }

}
