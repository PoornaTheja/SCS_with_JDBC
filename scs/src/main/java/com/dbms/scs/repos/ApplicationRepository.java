package com.dbms.scs.repos;

import com.dbms.scs.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
