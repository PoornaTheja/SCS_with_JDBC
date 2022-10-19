package com.dbms.scs.service;

import com.dbms.scs.domain.MonthlyReports;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.MonthlyReportsDTO;
import com.dbms.scs.repos.MonthlyReportsRepository;
import com.dbms.scs.repos.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MonthlyReportsService {

    private final MonthlyReportsRepository monthlyReportsRepository;
    private final StudentRepository studentRepository;

    public MonthlyReportsService(final MonthlyReportsRepository monthlyReportsRepository,
            final StudentRepository studentRepository) {
        this.monthlyReportsRepository = monthlyReportsRepository;
        this.studentRepository = studentRepository;
    }

    public List<MonthlyReportsDTO> findAll() {
        return monthlyReportsRepository.findAll(Sort.by("reportID"))
                .stream()
                .map(monthlyReports -> mapToDTO(monthlyReports, new MonthlyReportsDTO()))
                .collect(Collectors.toList());
    }

    public MonthlyReportsDTO get(final Integer reportID) {
        return monthlyReportsRepository.findById(reportID)
                .map(monthlyReports -> mapToDTO(monthlyReports, new MonthlyReportsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final MonthlyReportsDTO monthlyReportsDTO) {
        final MonthlyReports monthlyReports = new MonthlyReports();
        mapToEntity(monthlyReportsDTO, monthlyReports);
        return monthlyReportsRepository.save(monthlyReports).getReportID();
    }

    public void update(final Integer reportID, final MonthlyReportsDTO monthlyReportsDTO) {
        final MonthlyReports monthlyReports = monthlyReportsRepository.findById(reportID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(monthlyReportsDTO, monthlyReports);
        monthlyReportsRepository.save(monthlyReports);
    }

    public void delete(final Integer reportID) {
        monthlyReportsRepository.deleteById(reportID);
    }

    private MonthlyReportsDTO mapToDTO(final MonthlyReports monthlyReports,
            final MonthlyReportsDTO monthlyReportsDTO) {
        monthlyReportsDTO.setReportID(monthlyReports.getReportID());
        monthlyReportsDTO.setFromDate(monthlyReports.getFromDate());
        monthlyReportsDTO.setToDate(monthlyReports.getToDate());
        monthlyReportsDTO.setCompiledOn(monthlyReports.getCompiledOn());
        monthlyReportsDTO.setReportLink(monthlyReports.getReportLink());
        monthlyReportsDTO.setStudntcompilesreports(monthlyReports.getStudntcompilesreports() == null ? null : monthlyReports.getStudntcompilesreports().getStudentRollNo());
        return monthlyReportsDTO;
    }

    private MonthlyReports mapToEntity(final MonthlyReportsDTO monthlyReportsDTO,
            final MonthlyReports monthlyReports) {
        monthlyReports.setFromDate(monthlyReportsDTO.getFromDate());
        monthlyReports.setToDate(monthlyReportsDTO.getToDate());
        monthlyReports.setCompiledOn(monthlyReportsDTO.getCompiledOn());
        monthlyReports.setReportLink(monthlyReportsDTO.getReportLink());
        final Student studntcompilesreports = monthlyReportsDTO.getStudntcompilesreports() == null ? null : studentRepository.findById(monthlyReportsDTO.getStudntcompilesreports())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studntcompilesreports not found"));
        monthlyReports.setStudntcompilesreports(studntcompilesreports);
        return monthlyReports;
    }

}
