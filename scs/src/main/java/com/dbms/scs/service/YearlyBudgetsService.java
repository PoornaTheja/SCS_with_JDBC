package com.dbms.scs.service;

import com.dbms.scs.domain.YearlyBudgets;
import com.dbms.scs.model.YearlyBudgetsDTO;
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
public class YearlyBudgetsService {

    private final YearlyBudgetsRepository yearlyBudgetsRepository;

    public YearlyBudgetsService(final YearlyBudgetsRepository yearlyBudgetsRepository) {
        this.yearlyBudgetsRepository = yearlyBudgetsRepository;
    }

    public List<YearlyBudgetsDTO> findAll() {
        return yearlyBudgetsRepository.findAll(Sort.by("verticalID"))
                .stream()
                .map(yearlyBudgets -> mapToDTO(yearlyBudgets, new YearlyBudgetsDTO()))
                .collect(Collectors.toList());
    }

    public YearlyBudgetsDTO get(final Integer verticalID) {
        return yearlyBudgetsRepository.findById(verticalID)
                .map(yearlyBudgets -> mapToDTO(yearlyBudgets, new YearlyBudgetsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final YearlyBudgetsDTO yearlyBudgetsDTO) {
        final YearlyBudgets yearlyBudgets = new YearlyBudgets();
        mapToEntity(yearlyBudgetsDTO, yearlyBudgets);
        return yearlyBudgetsRepository.save(yearlyBudgets).getVerticalID();
    }

    public void update(final Integer verticalID, final YearlyBudgetsDTO yearlyBudgetsDTO) {
        final YearlyBudgets yearlyBudgets = yearlyBudgetsRepository.findById(verticalID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(yearlyBudgetsDTO, yearlyBudgets);
        yearlyBudgetsRepository.save(yearlyBudgets);
    }

    public void delete(final Integer verticalID) {
        yearlyBudgetsRepository.deleteById(verticalID);
    }

    private YearlyBudgetsDTO mapToDTO(final YearlyBudgets yearlyBudgets,
            final YearlyBudgetsDTO yearlyBudgetsDTO) {
        yearlyBudgetsDTO.setVerticalID(yearlyBudgets.getVerticalID());
        yearlyBudgetsDTO.setYear(yearlyBudgets.getYear());
        yearlyBudgetsDTO.setTotalAmount(yearlyBudgets.getTotalAmount());
        yearlyBudgetsDTO.setAmountSpent(yearlyBudgets.getAmountSpent());
        return yearlyBudgetsDTO;
    }

    private YearlyBudgets mapToEntity(final YearlyBudgetsDTO yearlyBudgetsDTO,
            final YearlyBudgets yearlyBudgets) {
        yearlyBudgets.setYear(yearlyBudgetsDTO.getYear());
        yearlyBudgets.setTotalAmount(yearlyBudgetsDTO.getTotalAmount());
        yearlyBudgets.setAmountSpent(yearlyBudgetsDTO.getAmountSpent());
        return yearlyBudgets;
    }

    @Transactional
    public String getReferencedWarning(final Integer verticalID) {
        final YearlyBudgets yearlyBudgets = yearlyBudgetsRepository.findById(verticalID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (yearlyBudgets.getVerticalBudgets() != null) {
            return WebUtils.getMessage("yearlyBudgets.vertical.oneToOne.referenced", yearlyBudgets.getVerticalBudgets().getVerticalID());
        }
        return null;
    }

}
