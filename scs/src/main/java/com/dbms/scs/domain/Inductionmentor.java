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
public class Inductionmentor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mentorID;

    @Column
    private Integer nodalMentorID;

    @OneToOne(
            mappedBy = "memberbecomesIM",
            fetch = FetchType.LAZY
    )
    private SCSMembers memberbecomesIM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_mmentoredbynm_id")
    private Inductionmentor iMmentoredbyNM;

    @OneToMany(mappedBy = "iMmentoredbyNM")
    private Set<Inductionmentor> iMmentoredbyNMInductionmentors;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_mpartof_induction_id")
    private InductionProgram iMpartofInduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imgrpcontainsim_id")
    private IMGroups iMGrpcontainsIM;

}
