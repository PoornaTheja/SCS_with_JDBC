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
public class Student {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentRollNo;

    @Column
    private String name;

    @Column
    private Integer yearOfjoin;

    @Column
    private String department;

    @Column
    private Integer yearOfGradn;

    @Column
    private String program;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private Long phoneNumber;

    @Column
    private String eMailId;

    @OneToMany(mappedBy = "counselsTo")
    private Set<CounsellingSessions> counselsToCounsellingSessionss;

    @OneToOne
    @JoinColumn(name = "student_passed_out_id")
    private AlumniAdvisors studentPassedOut;

    @OneToOne
    @JoinColumn(name = "scsmembership_id")
    private SCSMembers sCSMembership;

    @OneToMany(mappedBy = "studntcompilesreports")
    private Set<MonthlyReports> studntcompilesreportsMonthlyReportss;

    @OneToMany(mappedBy = "studentCompilesrepos")
    private Set<Repositories> studentCompilesreposRepositoriess;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "studparticipateevent",
            joinColumns = @JoinColumn(name = "student_student_roll_no"),
            inverseJoinColumns = @JoinColumn(name = "event_eventid")
    )
    private Set<Event> studparticipateeventEvents;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "studentawarded",
            joinColumns = @JoinColumn(name = "student_student_roll_no"),
            inverseJoinColumns = @JoinColumn(name = "award_awardid")
    )
    private Set<Award> studentawardedAwards;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "studinitiativepart",
            joinColumns = @JoinColumn(name = "student_student_roll_no"),
            inverseJoinColumns = @JoinColumn(name = "inititatives_initiatives_id")
    )
    private Set<Inititatives> studinitiativepartInititativess;

    @OneToMany(mappedBy = "studorderbooking")
    private Set<BookBookings> studorderbookingBookBookingss;

    @OneToMany(mappedBy = "studapplies")
    private Set<Application> studappliesApplications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studspartofimgp_id")
    private IMGroups studspartofIMgp;

}
