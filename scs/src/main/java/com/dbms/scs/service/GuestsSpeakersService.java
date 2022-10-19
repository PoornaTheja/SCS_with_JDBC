package com.dbms.scs.service;

import com.dbms.scs.domain.GuestsSpeakers;
import com.dbms.scs.model.GuestsSpeakersDTO;
import com.dbms.scs.repos.GuestsSpeakersRepository;
import com.dbms.scs.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class GuestsSpeakersService {

    private final GuestsSpeakersRepository guestsSpeakersRepository;

    public GuestsSpeakersService(final GuestsSpeakersRepository guestsSpeakersRepository) {
        this.guestsSpeakersRepository = guestsSpeakersRepository;
    }

    public List<GuestsSpeakersDTO> findAll() {
        return guestsSpeakersRepository.findAll(Sort.by("guestID"))
                .stream()
                .map(guestsSpeakers -> mapToDTO(guestsSpeakers, new GuestsSpeakersDTO()))
                .collect(Collectors.toList());
    }

    public GuestsSpeakersDTO get(final Integer guestID) {
        return guestsSpeakersRepository.findById(guestID)
                .map(guestsSpeakers -> mapToDTO(guestsSpeakers, new GuestsSpeakersDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final GuestsSpeakersDTO guestsSpeakersDTO) {
        final GuestsSpeakers guestsSpeakers = new GuestsSpeakers();
        mapToEntity(guestsSpeakersDTO, guestsSpeakers);
        return guestsSpeakersRepository.save(guestsSpeakers).getGuestID();
    }

    public void update(final Integer guestID, final GuestsSpeakersDTO guestsSpeakersDTO) {
        final GuestsSpeakers guestsSpeakers = guestsSpeakersRepository.findById(guestID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(guestsSpeakersDTO, guestsSpeakers);
        guestsSpeakersRepository.save(guestsSpeakers);
    }

    public void delete(final Integer guestID) {
        guestsSpeakersRepository.deleteById(guestID);
    }

    private GuestsSpeakersDTO mapToDTO(final GuestsSpeakers guestsSpeakers,
            final GuestsSpeakersDTO guestsSpeakersDTO) {
        guestsSpeakersDTO.setGuestID(guestsSpeakers.getGuestID());
        guestsSpeakersDTO.setName(guestsSpeakers.getName());
        guestsSpeakersDTO.setDesignation(guestsSpeakers.getDesignation());
        guestsSpeakersDTO.setAccountNumber(guestsSpeakers.getAccountNumber());
        guestsSpeakersDTO.setResources(guestsSpeakers.getResources());
        guestsSpeakersDTO.setEmailID(guestsSpeakers.getEmailID());
        guestsSpeakersDTO.setPhoneNumber(guestsSpeakers.getPhoneNumber());
        guestsSpeakersDTO.setQualification(guestsSpeakers.getQualification());
        return guestsSpeakersDTO;
    }

    private GuestsSpeakers mapToEntity(final GuestsSpeakersDTO guestsSpeakersDTO,
            final GuestsSpeakers guestsSpeakers) {
        guestsSpeakers.setName(guestsSpeakersDTO.getName());
        guestsSpeakers.setDesignation(guestsSpeakersDTO.getDesignation());
        guestsSpeakers.setAccountNumber(guestsSpeakersDTO.getAccountNumber());
        guestsSpeakers.setResources(guestsSpeakersDTO.getResources());
        guestsSpeakers.setEmailID(guestsSpeakersDTO.getEmailID());
        guestsSpeakers.setPhoneNumber(guestsSpeakersDTO.getPhoneNumber());
        guestsSpeakers.setQualification(guestsSpeakersDTO.getQualification());
        return guestsSpeakers;
    }

    @Transactional
    public String getReferencedWarning(final Integer guestID) {
        final GuestsSpeakers guestsSpeakers = guestsSpeakersRepository.findById(guestID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!guestsSpeakers.getHonoredToGuestsHonorariums().isEmpty()) {
            return WebUtils.getMessage("guestsSpeakers.honorarium.manyToOne.referenced", guestsSpeakers.getHonoredToGuestsHonorariums().iterator().next().getHonorariumID());
        }
        return null;
    }

}
