package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class IMGroups {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_mgroupsinprogram_id")
    private InductionProgram iMgroupsinprogram;

    @OneToOne
    @JoinColumn(name = "i_mgrpssupervisedby_fac_id")
    private Faculty iMgrpssupervisedbyFac;

    @OneToMany(mappedBy = "studspartofIMgp")
    private Set<Student> studspartofIMgpStudents;

    @OneToMany(mappedBy = "iMGrpcontainsIM")
    private Set<Inductionmentor> iMGrpcontainsIMInductionmentors;

}
