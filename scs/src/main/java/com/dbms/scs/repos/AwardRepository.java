package com.dbms.scs.repos;

import com.dbms.scs.domain.Award;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AwardRepository extends JpaRepository<Award, Integer> {
}
