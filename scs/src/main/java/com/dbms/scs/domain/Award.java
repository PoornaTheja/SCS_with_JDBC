package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Award {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer awardID;

    @Column
    private String awardType;

    @Column
    private String certificate;

    @Column
    private Integer year;

    @ManyToMany(mappedBy = "studentawardedAwards")
    private Set<Student> studentawardedStudents;

}
