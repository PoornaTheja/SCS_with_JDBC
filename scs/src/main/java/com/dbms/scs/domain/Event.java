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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Event {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventID;

    @Column
    private String resources;

    @Column
    private String minutesofMeeting;

    @Column
    private Integer noOfAttendees;

    @Column
    private LocalDate date;

    @Column
    private LocalTime fromTime;

    @Column
    private LocalTime toTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venued_at_id")
    private Venue venuedAt;

    @OneToOne
    @JoinColumn(name = "seekpermission_id")
    private EventsPermission seekpermission;

    @OneToMany(mappedBy = "honoredFor")
    private Set<Honorarium> honoredForHonorariums;

    @ManyToMany(mappedBy = "studparticipateeventEvents")
    private Set<Student> studparticipateeventStudents;

}
