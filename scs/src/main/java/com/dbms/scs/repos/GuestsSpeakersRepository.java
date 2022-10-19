package com.dbms.scs.repos;

import com.dbms.scs.domain.GuestsSpeakers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GuestsSpeakersRepository extends JpaRepository<GuestsSpeakers, Integer> {
}
