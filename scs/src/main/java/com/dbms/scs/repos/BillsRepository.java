package com.dbms.scs.repos;

import com.dbms.scs.domain.Bills;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillsRepository extends JpaRepository<Bills, Integer> {
}
