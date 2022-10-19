package com.dbms.scs.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Repositories {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repoID;

    @Column
    private Integer year;

    @Column
    private LocalDate compiledOn;

    @Column
    private String repoLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_compilesrepos_id")
    private Student studentCompilesrepos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vertical_repos_id")
    private Vertical verticalRepos;

}
