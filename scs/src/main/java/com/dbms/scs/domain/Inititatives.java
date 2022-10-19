package com.dbms.scs.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Inititatives {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer initiativesId;

    @Column
    private String title;

    @Column
    private LocalDate startDate;

    @Column
    private LocalTime timings;

    @Column
    private Long noOfAttendees;

    @Column
    private String resources;

    @Column
    private String minutesOfMeeting;

    @OneToOne
    @JoinColumn(name = "permissionforinitiative_id")
    private InitiativesPermission permissionforinitiative;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiatvehostedat_id")
    private Venue initiatvehostedat;

    @ManyToMany(mappedBy = "studinitiativepartInititativess")
    private Set<Student> studinitiativepartStudents;

}
