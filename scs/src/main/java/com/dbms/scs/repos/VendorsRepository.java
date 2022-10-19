package com.dbms.scs.repos;

import com.dbms.scs.domain.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorsRepository extends JpaRepository<Vendors, Integer> {
}
