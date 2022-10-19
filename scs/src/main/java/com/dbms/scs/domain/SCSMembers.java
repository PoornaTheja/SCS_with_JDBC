package com.dbms.scs.domain;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SCSMembers {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private String currentPosition;

    @OneToMany(mappedBy = "honorarirumResponsibles")
    private Set<Honorarium> honorarirumResponsiblesHonorariums;

    @OneToOne(mappedBy = "sCSMembership", fetch = FetchType.LAZY)
    private Student sCSMembership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verticalmemberbelongs_id")
    private Vertical verticalmemberbelongs;

    @OneToMany(mappedBy = "membertakespermission")
    private Set<EventsPermission> membertakespermissionEventsPermissions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membersresponsblebills_id")
    private Bills membersresponsblebills;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "membersattendmeet",
            joinColumns = @JoinColumn(name = "scsmembers_member_id"),
            inverseJoinColumns = @JoinColumn(name = "teammeetings_meetingid")
    )
    private Set<Teammeetings> membersattendmeetTeammeetingss;

    @OneToMany(mappedBy = "memberseekinitiativepermission")
    private Set<InitiativesPermission> memberseekinitiativepermissionInitiativesPermissions;

    @OneToOne(
            mappedBy = "applicantbecomesmember",
            fetch = FetchType.LAZY
    )
    private Application applicantbecomesmember;

    @OneToOne
    @JoinColumn(name = "memberbecomesim_id")
    private Inductionmentor memberbecomesIM;

}
