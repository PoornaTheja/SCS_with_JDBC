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
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Application {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationID;

    @Column
    private String isSelected;

    @Column
    private LocalDate dateofapplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationstovertical_id")
    private Vertical applicationstovertical;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicnforrecruitment_id")
    private Recruitment applicnforrecruitment;

    @OneToOne
    @JoinColumn(name = "applicantbecomesmember_id")
    private SCSMembers applicantbecomesmember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studapplies_id")
    private Student studapplies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicnpartofvertical_id")
    private Vertical applicnpartofvertical;

}
