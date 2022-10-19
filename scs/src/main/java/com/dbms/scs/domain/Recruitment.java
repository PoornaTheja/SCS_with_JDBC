package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Recruitment {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitmentID;

    @Column
    private Integer year;

    @Column
    private String pOR;

    @OneToMany(mappedBy = "applicnforrecruitment")
    private Set<Application> applicnforrecruitmentApplications;

}
