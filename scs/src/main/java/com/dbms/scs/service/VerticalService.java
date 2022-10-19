package com.dbms.scs.service;

import com.dbms.scs.domain.Vertical;
import com.dbms.scs.domain.YearlyBudgets;
import com.dbms.scs.model.VerticalDTO;
import com.dbms.scs.repos.VerticalRepository;
import com.dbms.scs.repos.YearlyBudgetsRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class VerticalService {

    private final VerticalRepository verticalRepository;
    private final YearlyBudgetsRepository yearlyBudgetsRepository;

    public VerticalService(final VerticalRepository verticalRepository,
            final YearlyBudgetsRepository yearlyBudgetsRepository) {
        this.verticalRepository = verticalRepository;
        this.yearlyBudgetsRepository = yearlyBudgetsRepository;
    }

    public List<VerticalDTO> findAll() {
        return verticalRepository.findAll(Sort.by("verticalID"))
                .stream()
                .map(vertical -> mapToDTO(vertical, new VerticalDTO()))
                .collect(Collectors.toList());
    }

    public VerticalDTO get(final Integer verticalID) {
        return verticalRepository.findById(verticalID)
                .map(vertical -> mapToDTO(vertical, new VerticalDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final VerticalDTO verticalDTO) {
        final Vertical vertical = new Vertical();
        mapToEntity(verticalDTO, vertical);
        return verticalRepository.save(vertical).getVerticalID();
    }

    public void update(final Integer verticalID, final VerticalDTO verticalDTO) {
        final Vertical vertical = verticalRepository.findById(verticalID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(verticalDTO, vertical);
        verticalRepository.save(vertical);
    }

    public void delete(final Integer verticalID) {
        verticalRepository.deleteById(verticalID);
    }

    private VerticalDTO mapToDTO(final Vertical vertical, final VerticalDTO verticalDTO) {
        verticalDTO.setVerticalID(vertical.getVerticalID());
        verticalDTO.setNameOfVertical(vertical.getNameOfVertical());
        verticalDTO.setVerticalBudgets(vertical.getVerticalBudgets() == null ? null : vertical.getVerticalBudgets().getVerticalID());
        return verticalDTO;
    }

    private Vertical mapToEntity(final VerticalDTO verticalDTO, final Vertical vertical) {
        vertical.setNameOfVertical(verticalDTO.getNameOfVertical());
        final YearlyBudgets verticalBudgets = verticalDTO.getVerticalBudgets() == null ? null : yearlyBudgetsRepository.findById(verticalDTO.getVerticalBudgets())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "verticalBudgets not found"));
        vertical.setVerticalBudgets(verticalBudgets);
        return vertical;
    }

    @Transactional
    public String getReferencedWarning(final Integer verticalID) {
        final Vertical vertical = verticalRepository.findById(verticalID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!vertical.getVerticalmemberbelongsSCSMemberss().isEmpty()) {
            return WebUtils.getMessage("vertical.sCSMembers.manyToOne.referenced", vertical.getVerticalmemberbelongsSCSMemberss().iterator().next().getMemberId());
        } else if (!vertical.getVerticalReposRepositoriess().isEmpty()) {
            return WebUtils.getMessage("vertical.repositories.oneToMany.referenced", vertical.getVerticalReposRepositoriess().iterator().next().getRepoID());
        } else if (!vertical.getApplicationstoverticalApplications().isEmpty()) {
            return WebUtils.getMessage("vertical.application.manyToOne.referenced", vertical.getApplicationstoverticalApplications().iterator().next().getApplicationID());
        } else if (!vertical.getFacultycounselsverticalsFacultys().isEmpty()) {
            return WebUtils.getMessage("vertical.faculty.manyToMany.referenced", vertical.getFacultycounselsverticalsFacultys().iterator().next().getFacultyId());
        } else if (vertical.getMeetbelongtovertical() != null) {
            return WebUtils.getMessage("vertical.teammeetings.oneToOne.referenced", vertical.getMeetbelongtovertical().getMeetingID());
        } else if (!vertical.getApplicnpartofverticalApplications().isEmpty()) {
            return WebUtils.getMessage("vertical.application.manyToOne.referenced", vertical.getApplicnpartofverticalApplications().iterator().next().getApplicationID());
        }
        return null;
    }

}
