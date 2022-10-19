package com.dbms.scs.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Teammeetings {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingID;

    @Column
    private Integer noOfAttendees;

    @Column
    private String kindOfMeeting;

    @Column
    private LocalDate date;

    @Column
    private LocalTime fromTime;

    @Column
    private LocalTime toTime;

    @Column
    private String location;

    @Column
    private String resourcesLink;

    @ManyToMany(mappedBy = "membersattendmeetTeammeetingss")
    private Set<SCSMembers> membersattendmeetSCSMemberss;

    @OneToOne
    @JoinColumn(name = "meetbelongtovertical_id")
    private Vertical meetbelongtovertical;

}
