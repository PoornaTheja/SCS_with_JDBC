package com.dbms.scs.service;

import com.dbms.scs.domain.Application;
import com.dbms.scs.domain.Recruitment;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.domain.Student;
import com.dbms.scs.domain.Vertical;
import com.dbms.scs.model.ApplicationDTO;
import com.dbms.scs.repos.ApplicationRepository;
import com.dbms.scs.repos.RecruitmentRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.repos.VerticalRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final VerticalRepository verticalRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final SCSMembersRepository sCSMembersRepository;
    private final StudentRepository studentRepository;

    public ApplicationService(final ApplicationRepository applicationRepository,
            final VerticalRepository verticalRepository,
            final RecruitmentRepository recruitmentRepository,
            final SCSMembersRepository sCSMembersRepository,
            final StudentRepository studentRepository) {
        this.applicationRepository = applicationRepository;
        this.verticalRepository = verticalRepository;
        this.recruitmentRepository = recruitmentRepository;
        this.sCSMembersRepository = sCSMembersRepository;
        this.studentRepository = studentRepository;
    }

    public List<ApplicationDTO> findAll() {
        return applicationRepository.findAll(Sort.by("applicationID"))
                .stream()
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .collect(Collectors.toList());
    }

    public ApplicationDTO get(final Long applicationID) {
        return applicationRepository.findById(applicationID)
                .map(application -> mapToDTO(application, new ApplicationDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ApplicationDTO applicationDTO) {
        final Application application = new Application();
        mapToEntity(applicationDTO, application);
        return applicationRepository.save(application).getApplicationID();
    }

    public void update(final Long applicationID, final ApplicationDTO applicationDTO) {
        final Application application = applicationRepository.findById(applicationID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(applicationDTO, application);
        applicationRepository.save(application);
    }

    public void delete(final Long applicationID) {
        applicationRepository.deleteById(applicationID);
    }

    private ApplicationDTO mapToDTO(final Application application,
            final ApplicationDTO applicationDTO) {
        applicationDTO.setApplicationID(application.getApplicationID());
        applicationDTO.setIsSelected(application.getIsSelected());
        applicationDTO.setDateofapplication(application.getDateofapplication());
        applicationDTO.setApplicationstovertical(application.getApplicationstovertical() == null ? null : application.getApplicationstovertical().getVerticalID());
        applicationDTO.setApplicnforrecruitment(application.getApplicnforrecruitment() == null ? null : application.getApplicnforrecruitment().getRecruitmentID());
        applicationDTO.setApplicantbecomesmember(application.getApplicantbecomesmember() == null ? null : application.getApplicantbecomesmember().getMemberId());
        applicationDTO.setStudapplies(application.getStudapplies() == null ? null : application.getStudapplies().getStudentRollNo());
        applicationDTO.setApplicnpartofvertical(application.getApplicnpartofvertical() == null ? null : application.getApplicnpartofvertical().getVerticalID());
        return applicationDTO;
    }

    private Application mapToEntity(final ApplicationDTO applicationDTO,
            final Application application) {
        application.setIsSelected(applicationDTO.getIsSelected());
        application.setDateofapplication(applicationDTO.getDateofapplication());
        final Vertical applicationstovertical = applicationDTO.getApplicationstovertical() == null ? null : verticalRepository.findById(applicationDTO.getApplicationstovertical())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicationstovertical not found"));
        application.setApplicationstovertical(applicationstovertical);
        final Recruitment applicnforrecruitment = applicationDTO.getApplicnforrecruitment() == null ? null : recruitmentRepository.findById(applicationDTO.getApplicnforrecruitment())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicnforrecruitment not found"));
        application.setApplicnforrecruitment(applicnforrecruitment);
        final SCSMembers applicantbecomesmember = applicationDTO.getApplicantbecomesmember() == null ? null : sCSMembersRepository.findById(applicationDTO.getApplicantbecomesmember())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicantbecomesmember not found"));
        application.setApplicantbecomesmember(applicantbecomesmember);
        final Student studapplies = applicationDTO.getStudapplies() == null ? null : studentRepository.findById(applicationDTO.getStudapplies())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studapplies not found"));
        application.setStudapplies(studapplies);
        final Vertical applicnpartofvertical = applicationDTO.getApplicnpartofvertical() == null ? null : verticalRepository.findById(applicationDTO.getApplicnpartofvertical())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "applicnpartofvertical not found"));
        application.setApplicnpartofvertical(applicnpartofvertical);
        return application;
    }

}
