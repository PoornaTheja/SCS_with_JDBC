package com.dbms.scs.service;

import com.dbms.scs.domain.Event;
import com.dbms.scs.domain.EventsPermission;
import com.dbms.scs.domain.Venue;
import com.dbms.scs.model.EventDTO;
import com.dbms.scs.repos.EventRepository;
import com.dbms.scs.repos.EventsPermissionRepository;
import com.dbms.scs.repos.VenueRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final EventsPermissionRepository eventsPermissionRepository;

    public EventService(final EventRepository eventRepository,
            final VenueRepository venueRepository,
            final EventsPermissionRepository eventsPermissionRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.eventsPermissionRepository = eventsPermissionRepository;
    }

    public List<EventDTO> findAll() {
        return eventRepository.findAll(Sort.by("eventID"))
                .stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .collect(Collectors.toList());
    }

    public EventDTO get(final Integer eventID) {
        return eventRepository.findById(eventID)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getEventID();
    }

    public void update(final Integer eventID, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(eventID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Integer eventID) {
        eventRepository.deleteById(eventID);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setEventID(event.getEventID());
        eventDTO.setResources(event.getResources());
        eventDTO.setMinutesofMeeting(event.getMinutesofMeeting());
        eventDTO.setNoOfAttendees(event.getNoOfAttendees());
        eventDTO.setDate(event.getDate());
        eventDTO.setFromTime(event.getFromTime());
        eventDTO.setToTime(event.getToTime());
        eventDTO.setVenuedAt(event.getVenuedAt() == null ? null : event.getVenuedAt().getVenueID());
        eventDTO.setSeekpermission(event.getSeekpermission() == null ? null : event.getSeekpermission().getPermissionID());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setResources(eventDTO.getResources());
        event.setMinutesofMeeting(eventDTO.getMinutesofMeeting());
        event.setNoOfAttendees(eventDTO.getNoOfAttendees());
        event.setDate(eventDTO.getDate());
        event.setFromTime(eventDTO.getFromTime());
        event.setToTime(eventDTO.getToTime());
        final Venue venuedAt = eventDTO.getVenuedAt() == null ? null : venueRepository.findById(eventDTO.getVenuedAt())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "venuedAt not found"));
        event.setVenuedAt(venuedAt);
        final EventsPermission seekpermission = eventDTO.getSeekpermission() == null ? null : eventsPermissionRepository.findById(eventDTO.getSeekpermission())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "seekpermission not found"));
        event.setSeekpermission(seekpermission);
        return event;
    }

    @Transactional
    public String getReferencedWarning(final Integer eventID) {
        final Event event = eventRepository.findById(eventID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!event.getHonoredForHonorariums().isEmpty()) {
            return WebUtils.getMessage("event.honorarium.oneToMany.referenced", event.getHonoredForHonorariums().iterator().next().getHonorariumID());
        } else if (!event.getStudparticipateeventStudents().isEmpty()) {
            return WebUtils.getMessage("event.student.manyToMany.referenced", event.getStudparticipateeventStudents().iterator().next().getStudentRollNo());
        }
        return null;
    }

}
