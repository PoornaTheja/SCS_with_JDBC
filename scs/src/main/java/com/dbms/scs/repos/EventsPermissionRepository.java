package com.dbms.scs.repos;

import com.dbms.scs.domain.EventsPermission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventsPermissionRepository extends JpaRepository<EventsPermission, Integer> {
}
