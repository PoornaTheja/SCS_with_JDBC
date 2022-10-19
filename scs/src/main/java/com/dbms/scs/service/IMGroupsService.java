package com.dbms.scs.service;

import com.dbms.scs.domain.Faculty;
import com.dbms.scs.domain.IMGroups;
import com.dbms.scs.domain.InductionProgram;
import com.dbms.scs.model.IMGroupsDTO;
import com.dbms.scs.repos.FacultyRepository;
import com.dbms.scs.repos.IMGroupsRepository;
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
public class IMGroupsService {

    private final IMGroupsRepository iMGroupsRepository;
    private final InductionProgramRepository inductionProgramRepository;
    private final FacultyRepository facultyRepository;

    public IMGroupsService(final IMGroupsRepository iMGroupsRepository,
            final InductionProgramRepository inductionProgramRepository,
            final FacultyRepository facultyRepository) {
        this.iMGroupsRepository = iMGroupsRepository;
        this.inductionProgramRepository = inductionProgramRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<IMGroupsDTO> findAll() {
        return iMGroupsRepository.findAll(Sort.by("groupID"))
                .stream()
                .map(iMGroups -> mapToDTO(iMGroups, new IMGroupsDTO()))
                .collect(Collectors.toList());
    }

    public IMGroupsDTO get(final Integer groupID) {
        return iMGroupsRepository.findById(groupID)
                .map(iMGroups -> mapToDTO(iMGroups, new IMGroupsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final IMGroupsDTO iMGroupsDTO) {
        final IMGroups iMGroups = new IMGroups();
        mapToEntity(iMGroupsDTO, iMGroups);
        return iMGroupsRepository.save(iMGroups).getGroupID();
    }

    public void update(final Integer groupID, final IMGroupsDTO iMGroupsDTO) {
        final IMGroups iMGroups = iMGroupsRepository.findById(groupID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(iMGroupsDTO, iMGroups);
        iMGroupsRepository.save(iMGroups);
    }

    public void delete(final Integer groupID) {
        iMGroupsRepository.deleteById(groupID);
    }

    private IMGroupsDTO mapToDTO(final IMGroups iMGroups, final IMGroupsDTO iMGroupsDTO) {
        iMGroupsDTO.setGroupID(iMGroups.getGroupID());
        iMGroupsDTO.setIMgroupsinprogram(iMGroups.getIMgroupsinprogram() == null ? null : iMGroups.getIMgroupsinprogram().getInductionId());
        iMGroupsDTO.setIMgrpssupervisedbyFac(iMGroups.getIMgrpssupervisedbyFac() == null ? null : iMGroups.getIMgrpssupervisedbyFac().getFacultyId());
        return iMGroupsDTO;
    }

    private IMGroups mapToEntity(final IMGroupsDTO iMGroupsDTO, final IMGroups iMGroups) {
        final InductionProgram iMgroupsinprogram = iMGroupsDTO.getIMgroupsinprogram() == null ? null : inductionProgramRepository.findById(iMGroupsDTO.getIMgroupsinprogram())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "iMgroupsinprogram not found"));
        iMGroups.setIMgroupsinprogram(iMgroupsinprogram);
        final Faculty iMgrpssupervisedbyFac = iMGroupsDTO.getIMgrpssupervisedbyFac() == null ? null : facultyRepository.findById(iMGroupsDTO.getIMgrpssupervisedbyFac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "iMgrpssupervisedbyFac not found"));
        iMGroups.setIMgrpssupervisedbyFac(iMgrpssupervisedbyFac);
        return iMGroups;
    }

    @Transactional
    public String getReferencedWarning(final Integer groupID) {
        final IMGroups iMGroups = iMGroupsRepository.findById(groupID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!iMGroups.getStudspartofIMgpStudents().isEmpty()) {
            return WebUtils.getMessage("iMGroups.student.manyToOne.referenced", iMGroups.getStudspartofIMgpStudents().iterator().next().getStudentRollNo());
        } else if (!iMGroups.getIMGrpcontainsIMInductionmentors().isEmpty()) {
            return WebUtils.getMessage("iMGroups.inductionmentor.oneToMany.referenced", iMGroups.getIMGrpcontainsIMInductionmentors().iterator().next().getMentorID());
        }
        return null;
    }

}
