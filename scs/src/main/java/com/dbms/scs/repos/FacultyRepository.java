package com.dbms.scs.repos;

import com.dbms.scs.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
