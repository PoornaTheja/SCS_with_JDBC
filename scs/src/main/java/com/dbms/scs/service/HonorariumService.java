package com.dbms.scs.service;

import com.dbms.scs.domain.Event;
import com.dbms.scs.domain.GuestsSpeakers;
import com.dbms.scs.domain.Honorarium;
import com.dbms.scs.domain.SCSMembers;
import com.dbms.scs.model.HonorariumDTO;
import com.dbms.scs.repos.EventRepository;
import com.dbms.scs.repos.GuestsSpeakersRepository;
import com.dbms.scs.repos.HonorariumRepository;
import com.dbms.scs.repos.SCSMembersRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class HonorariumService {

    private final HonorariumRepository honorariumRepository;
    private final EventRepository eventRepository;
    private final GuestsSpeakersRepository guestsSpeakersRepository;
    private final SCSMembersRepository sCSMembersRepository;

    public HonorariumService(final HonorariumRepository honorariumRepository,
            final EventRepository eventRepository,
            final GuestsSpeakersRepository guestsSpeakersRepository,
            final SCSMembersRepository sCSMembersRepository) {
        this.honorariumRepository = honorariumRepository;
        this.eventRepository = eventRepository;
        this.guestsSpeakersRepository = guestsSpeakersRepository;
        this.sCSMembersRepository = sCSMembersRepository;
    }

    public List<HonorariumDTO> findAll() {
        return honorariumRepository.findAll(Sort.by("honorariumID"))
                .stream()
                .map(honorarium -> mapToDTO(honorarium, new HonorariumDTO()))
                .collect(Collectors.toList());
    }

    public HonorariumDTO get(final Integer honorariumID) {
        return honorariumRepository.findById(honorariumID)
                .map(honorarium -> mapToDTO(honorarium, new HonorariumDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final HonorariumDTO honorariumDTO) {
        final Honorarium honorarium = new Honorarium();
        mapToEntity(honorariumDTO, honorarium);
        return honorariumRepository.save(honorarium).getHonorariumID();
    }

    public void update(final Integer honorariumID, final HonorariumDTO honorariumDTO) {
        final Honorarium honorarium = honorariumRepository.findById(honorariumID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(honorariumDTO, honorarium);
        honorariumRepository.save(honorarium);
    }

    public void delete(final Integer honorariumID) {
        honorariumRepository.deleteById(honorariumID);
    }

    private HonorariumDTO mapToDTO(final Honorarium honorarium, final HonorariumDTO honorariumDTO) {
        honorariumDTO.setHonorariumID(honorarium.getHonorariumID());
        honorariumDTO.setGuestFeedback(honorarium.getGuestFeedback());
        honorariumDTO.setAmount(honorarium.getAmount());
        honorariumDTO.setStatus(honorarium.getStatus());
        honorariumDTO.setTransactionNumber(honorarium.getTransactionNumber());
        honorariumDTO.setHonoredFor(honorarium.getHonoredFor() == null ? null : honorarium.getHonoredFor().getEventID());
        honorariumDTO.setHonoredToGuests(honorarium.getHonoredToGuests() == null ? null : honorarium.getHonoredToGuests().getGuestID());
        honorariumDTO.setHonorarirumResponsibles(honorarium.getHonorarirumResponsibles() == null ? null : honorarium.getHonorarirumResponsibles().getMemberId());
        return honorariumDTO;
    }

    private Honorarium mapToEntity(final HonorariumDTO honorariumDTO, final Honorarium honorarium) {
        honorarium.setGuestFeedback(honorariumDTO.getGuestFeedback());
        honorarium.setAmount(honorariumDTO.getAmount());
        honorarium.setStatus(honorariumDTO.getStatus());
        honorarium.setTransactionNumber(honorariumDTO.getTransactionNumber());
        final Event honoredFor = honorariumDTO.getHonoredFor() == null ? null : eventRepository.findById(honorariumDTO.getHonoredFor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "honoredFor not found"));
        honorarium.setHonoredFor(honoredFor);
        final GuestsSpeakers honoredToGuests = honorariumDTO.getHonoredToGuests() == null ? null : guestsSpeakersRepository.findById(honorariumDTO.getHonoredToGuests())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "honoredToGuests not found"));
        honorarium.setHonoredToGuests(honoredToGuests);
        final SCSMembers honorarirumResponsibles = honorariumDTO.getHonorarirumResponsibles() == null ? null : sCSMembersRepository.findById(honorariumDTO.getHonorarirumResponsibles())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "honorarirumResponsibles not found"));
        honorarium.setHonorarirumResponsibles(honorarirumResponsibles);
        return honorarium;
    }

}
