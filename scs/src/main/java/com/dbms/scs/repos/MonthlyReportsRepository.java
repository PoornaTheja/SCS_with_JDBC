package com.dbms.scs.repos;

import com.dbms.scs.domain.MonthlyReports;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MonthlyReportsRepository extends JpaRepository<MonthlyReports, Integer> {
}
