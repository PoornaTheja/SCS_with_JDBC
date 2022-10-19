package com.dbms.scs.repos;

import com.dbms.scs.domain.CounsellingSessions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CounsellingSessionsRepository extends JpaRepository<CounsellingSessions, Integer> {
}
