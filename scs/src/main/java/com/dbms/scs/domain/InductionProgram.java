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
public class InductionProgram {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inductionId;

    @Column
    private Integer year;

    @Column
    private String duration;

    @Column
    private String chairman;

    @Column
    private Integer noOfAttendees;

    @OneToMany(mappedBy = "iMpartofInduction")
    private Set<Inductionmentor> iMpartofInductionInductionmentors;

    @OneToMany(mappedBy = "iMgroupsinprogram")
    private Set<IMGroups> iMgroupsinprogramIMGroupss;

}
