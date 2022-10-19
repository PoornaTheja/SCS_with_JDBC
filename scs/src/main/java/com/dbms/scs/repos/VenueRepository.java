package com.dbms.scs.repos;

import com.dbms.scs.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VenueRepository extends JpaRepository<Venue, Integer> {
}
