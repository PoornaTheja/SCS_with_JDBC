package com.dbms.scs.service;

import com.dbms.scs.domain.AlumniAdvisors;
import com.dbms.scs.domain.Award;
import com.dbms.scs.domain.Event;
import com.dbms.scs.domain.IMGroups;
import com.dbms.scs.domain.Inititatives;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.StudentDTO;
import com.dbms.scs.repos.AlumniAdvisorsRepository;
import com.dbms.scs.repos.AwardRepository;
import com.dbms.scs.repos.EventRepository;
import com.dbms.scs.repos.IMGroupsRepository;
import com.dbms.scs.repos.InititativesRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import com.dbms.scs.repos.StudentRepository;
import com.dbms.scs.util.WebUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AlumniAdvisorsRepository alumniAdvisorsRepository;
    private final SCSMembersRepository sCSMembersRepository;
    private final EventRepository eventRepository;
    private final AwardRepository awardRepository;
    private final InititativesRepository inititativesRepository;
    private final IMGroupsRepository iMGroupsRepository;

    public StudentService(final StudentRepository studentRepository,
            final AlumniAdvisorsRepository alumniAdvisorsRepository,
            final SCSMembersRepository sCSMembersRepository, final EventRepository eventRepository,
            final AwardRepository awardRepository,
            final InititativesRepository inititativesRepository,
            final IMGroupsRepository iMGroupsRepository) {
        this.studentRepository = studentRepository;
        this.alumniAdvisorsRepository = alumniAdvisorsRepository;
        this.sCSMembersRepository = sCSMembersRepository;
        this.eventRepository = eventRepository;
        this.awardRepository = awardRepository;
        this.inititativesRepository = inititativesRepository;
        this.iMGroupsRepository = iMGroupsRepository;
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll(Sort.by("studentRollNo"))
                .stream()
                .map(student -> mapToDTO(student, new StudentDTO()))
                .collect(Collectors.toList());
    }

    public StudentDTO get(final Integer studentRollNo) {
        return studentRepository.findById(studentRollNo)
                .map(student -> mapToDTO(student, new StudentDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final StudentDTO studentDTO) {
        final Student student = new Student();
        mapToEntity(studentDTO, student);
        return studentRepository.save(student).getStudentRollNo();
    }

    public void update(final Integer studentRollNo, final StudentDTO studentDTO) {
        final Student student = studentRepository.findById(studentRollNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(studentDTO, student);
        studentRepository.save(student);
    }

    public void delete(final Integer studentRollNo) {
        studentRepository.deleteById(studentRollNo);
    }

    private StudentDTO mapToDTO(final Student student, final StudentDTO studentDTO) {
        studentDTO.setStudentRollNo(student.getStudentRollNo());
        studentDTO.setName(student.getName());
        studentDTO.setYearOfjoin(student.getYearOfjoin());
        studentDTO.setDepartment(student.getDepartment());
        studentDTO.setYearOfGradn(student.getYearOfGradn());
        studentDTO.setProgram(student.getProgram());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setPhoneNumber(student.getPhoneNumber());
        studentDTO.setEMailId(student.getEMailId());
        studentDTO.setStudentPassedOut(student.getStudentPassedOut() == null ? null : student.getStudentPassedOut().getAlumniID());
        studentDTO.setSCSMembership(student.getSCSMembership() == null ? null : student.getSCSMembership().getMemberId());
        studentDTO.setStudparticipateevents(student.getStudparticipateeventEvents() == null ? null : student.getStudparticipateeventEvents().stream()
                .map(event -> event.getEventID())
                .collect(Collectors.toList()));
        studentDTO.setStudentawardeds(student.getStudentawardedAwards() == null ? null : student.getStudentawardedAwards().stream()
                .map(award -> award.getAwardID())
                .collect(Collectors.toList()));
        studentDTO.setStudinitiativeparts(student.getStudinitiativepartInititativess() == null ? null : student.getStudinitiativepartInititativess().stream()
                .map(inititatives -> inititatives.getInitiativesId())
                .collect(Collectors.toList()));
        studentDTO.setStudspartofIMgp(student.getStudspartofIMgp() == null ? null : student.getStudspartofIMgp().getGroupID());
        return studentDTO;
    }

    private Student mapToEntity(final StudentDTO studentDTO, final Student student) {
        student.setName(studentDTO.getName());
        student.setYearOfjoin(studentDTO.getYearOfjoin());
        student.setDepartment(studentDTO.getDepartment());
        student.setYearOfGradn(studentDTO.getYearOfGradn());
        student.setProgram(studentDTO.getProgram());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setEMailId(studentDTO.getEMailId());
        final AlumniAdvisors studentPassedOut = studentDTO.getStudentPassedOut() == null ? null : alumniAdvisorsRepository.findById(studentDTO.getStudentPassedOut())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studentPassedOut not found"));
        student.setStudentPassedOut(studentPassedOut);
        final SCSMembers sCSMembership = studentDTO.getSCSMembership() == null ? null : sCSMembersRepository.findById(studentDTO.getSCSMembership())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sCSMembership not found"));
        student.setSCSMembership(sCSMembership);
        final List<Event> studparticipateevents = eventRepository.findAllById(
                studentDTO.getStudparticipateevents() == null ? Collections.emptyList() : studentDTO.getStudparticipateevents());
        if (studparticipateevents.size() != (studentDTO.getStudparticipateevents() == null ? 0 : studentDTO.getStudparticipateevents().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of studparticipateevents not found");
        }
        student.setStudparticipateeventEvents(studparticipateevents.stream().collect(Collectors.toSet()));
        final List<Award> studentawardeds = awardRepository.findAllById(
                studentDTO.getStudentawardeds() == null ? Collections.emptyList() : studentDTO.getStudentawardeds());
        if (studentawardeds.size() != (studentDTO.getStudentawardeds() == null ? 0 : studentDTO.getStudentawardeds().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of studentawardeds not found");
        }
        student.setStudentawardedAwards(studentawardeds.stream().collect(Collectors.toSet()));
        final List<Inititatives> studinitiativeparts = inititativesRepository.findAllById(
                studentDTO.getStudinitiativeparts() == null ? Collections.emptyList() : studentDTO.getStudinitiativeparts());
        if (studinitiativeparts.size() != (studentDTO.getStudinitiativeparts() == null ? 0 : studentDTO.getStudinitiativeparts().size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of studinitiativeparts not found");
        }
        student.setStudinitiativepartInititativess(studinitiativeparts.stream().collect(Collectors.toSet()));
        final IMGroups studspartofIMgp = studentDTO.getStudspartofIMgp() == null ? null : iMGroupsRepository.findById(studentDTO.getStudspartofIMgp())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studspartofIMgp not found"));
        student.setStudspartofIMgp(studspartofIMgp);
        return student;
    }

    @Transactional
    public String getReferencedWarning(final Integer studentRollNo) {
        final Student student = studentRepository.findById(studentRollNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!student.getCounselsToCounsellingSessionss().isEmpty()) {
            return WebUtils.getMessage("student.counsellingSessions.manyToOne.referenced", student.getCounselsToCounsellingSessionss().iterator().next().getSessionID());
        } else if (!student.getStudntcompilesreportsMonthlyReportss().isEmpty()) {
            return WebUtils.getMessage("student.monthlyReports.oneToMany.referenced", student.getStudntcompilesreportsMonthlyReportss().iterator().next().getReportID());
        } else if (!student.getStudentCompilesreposRepositoriess().isEmpty()) {
            return WebUtils.getMessage("student.repositories.oneToMany.referenced", student.getStudentCompilesreposRepositoriess().iterator().next().getRepoID());
        } else if (!student.getStudorderbookingBookBookingss().isEmpty()) {
            return WebUtils.getMessage("student.bookBookings.oneToMany.referenced", student.getStudorderbookingBookBookingss().iterator().next().getBookingid());
        } else if (!student.getStudappliesApplications().isEmpty()) {
            return WebUtils.getMessage("student.application.oneToMany.referenced", student.getStudappliesApplications().iterator().next().getApplicationID());
        }
        return null;
    }

}
