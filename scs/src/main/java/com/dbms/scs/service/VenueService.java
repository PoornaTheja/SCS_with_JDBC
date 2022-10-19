package com.dbms.scs.service;

import com.dbms.scs.domain.Venue;
import com.dbms.scs.model.VenueDTO;
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
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(final VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueDTO> findAll() {
        return venueRepository.findAll(Sort.by("venueID"))
                .stream()
                .map(venue -> mapToDTO(venue, new VenueDTO()))
                .collect(Collectors.toList());
    }

    public VenueDTO get(final Integer venueID) {
        return venueRepository.findById(venueID)
                .map(venue -> mapToDTO(venue, new VenueDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final VenueDTO venueDTO) {
        final Venue venue = new Venue();
        mapToEntity(venueDTO, venue);
        return venueRepository.save(venue).getVenueID();
    }

    public void update(final Integer venueID, final VenueDTO venueDTO) {
        final Venue venue = venueRepository.findById(venueID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(venueDTO, venue);
        venueRepository.save(venue);
    }

    public void delete(final Integer venueID) {
        venueRepository.deleteById(venueID);
    }

    private VenueDTO mapToDTO(final Venue venue, final VenueDTO venueDTO) {
        venueDTO.setVenueID(venue.getVenueID());
        venueDTO.setVenueName(venue.getVenueName());
        venueDTO.setCapacity(venue.getCapacity());
        venueDTO.setContactperson(venue.getContactperson());
        venueDTO.setContactnumber(venue.getContactnumber());
        venueDTO.setLocation(venue.getLocation());
        return venueDTO;
    }

    private Venue mapToEntity(final VenueDTO venueDTO, final Venue venue) {
        venue.setVenueName(venueDTO.getVenueName());
        venue.setCapacity(venueDTO.getCapacity());
        venue.setContactperson(venueDTO.getContactperson());
        venue.setContactnumber(venueDTO.getContactnumber());
        venue.setLocation(venueDTO.getLocation());
        return venue;
    }

    @Transactional
    public String getReferencedWarning(final Integer venueID) {
        final Venue venue = venueRepository.findById(venueID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!venue.getVenuedAtEvents().isEmpty()) {
            return WebUtils.getMessage("venue.event.manyToOne.referenced", venue.getVenuedAtEvents().iterator().next().getEventID());
        } else if (!venue.getInitiatvehostedatInititativess().isEmpty()) {
            return WebUtils.getMessage("venue.inititatives.manyToOne.referenced", venue.getInitiatvehostedatInititativess().iterator().next().getInitiativesId());
        }
        return null;
    }

}
