package com.dbms.scs.service;

import com.dbms.scs.domain.CounsellingSessions;
import com.dbms.scs.domain.Counselor;
import com.dbms.scs.domain.Student;
import com.dbms.scs.model.CounsellingSessionsDTO;
import com.dbms.scs.repos.CounsellingSessionsRepository;
import com.dbms.scs.repos.CounselorRepository;
import com.dbms.scs.repos.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CounsellingSessionsService {

    private final CounsellingSessionsRepository counsellingSessionsRepository;
    private final CounselorRepository counselorRepository;
    private final StudentRepository studentRepository;

    public CounsellingSessionsService(
            final CounsellingSessionsRepository counsellingSessionsRepository,
            final CounselorRepository counselorRepository,
            final StudentRepository studentRepository) {
        this.counsellingSessionsRepository = counsellingSessionsRepository;
        this.counselorRepository = counselorRepository;
        this.studentRepository = studentRepository;
    }

    public List<CounsellingSessionsDTO> findAll() {
        return counsellingSessionsRepository.findAll(Sort.by("sessionID"))
                .stream()
                .map(counsellingSessions -> mapToDTO(counsellingSessions, new CounsellingSessionsDTO()))
                .collect(Collectors.toList());
    }

    public CounsellingSessionsDTO get(final Integer sessionID) {
        return counsellingSessionsRepository.findById(sessionID)
                .map(counsellingSessions -> mapToDTO(counsellingSessions, new CounsellingSessionsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final CounsellingSessionsDTO counsellingSessionsDTO) {
        final CounsellingSessions counsellingSessions = new CounsellingSessions();
        mapToEntity(counsellingSessionsDTO, counsellingSessions);
        return counsellingSessionsRepository.save(counsellingSessions).getSessionID();
    }

    public void update(final Integer sessionID,
            final CounsellingSessionsDTO counsellingSessionsDTO) {
        final CounsellingSessions counsellingSessions = counsellingSessionsRepository.findById(sessionID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(counsellingSessionsDTO, counsellingSessions);
        counsellingSessionsRepository.save(counsellingSessions);
    }

    public void delete(final Integer sessionID) {
        counsellingSessionsRepository.deleteById(sessionID);
    }

    private CounsellingSessionsDTO mapToDTO(final CounsellingSessions counsellingSessions,
            final CounsellingSessionsDTO counsellingSessionsDTO) {
        counsellingSessionsDTO.setSessionID(counsellingSessions.getSessionID());
        counsellingSessionsDTO.setFeedback(counsellingSessions.getFeedback());
        counsellingSessionsDTO.setToTime(counsellingSessions.getToTime());
        counsellingSessionsDTO.setFromTime(counsellingSessions.getFromTime());
        counsellingSessionsDTO.setDate(counsellingSessions.getDate());
        counsellingSessionsDTO.setCounseledBy(counsellingSessions.getCounseledBy() == null ? null : counsellingSessions.getCounseledBy().getCounselorId());
        counsellingSessionsDTO.setCounselsTo(counsellingSessions.getCounselsTo() == null ? null : counsellingSessions.getCounselsTo().getStudentRollNo());
        return counsellingSessionsDTO;
    }

    private CounsellingSessions mapToEntity(final CounsellingSessionsDTO counsellingSessionsDTO,
            final CounsellingSessions counsellingSessions) {
        counsellingSessions.setFeedback(counsellingSessionsDTO.getFeedback());
        counsellingSessions.setToTime(counsellingSessionsDTO.getToTime());
        counsellingSessions.setFromTime(counsellingSessionsDTO.getFromTime());
        counsellingSessions.setDate(counsellingSessionsDTO.getDate());
        final Counselor counseledBy = counsellingSessionsDTO.getCounseledBy() == null ? null : counselorRepository.findById(counsellingSessionsDTO.getCounseledBy())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "counseledBy not found"));
        counsellingSessions.setCounseledBy(counseledBy);
        final Student counselsTo = counsellingSessionsDTO.getCounselsTo() == null ? null : studentRepository.findById(counsellingSessionsDTO.getCounselsTo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "counselsTo not found"));
        counsellingSessions.setCounselsTo(counselsTo);
        return counsellingSessions;
    }

}
