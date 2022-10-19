package com.dbms.scs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class AlumniAdvisors {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alumniID;

    @OneToOne(
            mappedBy = "studentPassedOut",
            fetch = FetchType.LAZY
    )
    private Student studentPassedOut;

}
