package com.dbms.scs.repos;

import com.dbms.scs.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Integer> {
}
