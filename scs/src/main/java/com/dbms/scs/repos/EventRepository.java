package com.dbms.scs.repos;

import com.dbms.scs.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Integer> {
}
