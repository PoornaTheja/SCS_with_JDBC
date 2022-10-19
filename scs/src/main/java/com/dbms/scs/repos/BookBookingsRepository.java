package com.dbms.scs.repos;

import com.dbms.scs.domain.BookBookings;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookBookingsRepository extends JpaRepository<BookBookings, Integer> {
}
