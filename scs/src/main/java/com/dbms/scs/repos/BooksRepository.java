package com.dbms.scs.repos;

import com.dbms.scs.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BooksRepository extends JpaRepository<Books, Integer> {
}
