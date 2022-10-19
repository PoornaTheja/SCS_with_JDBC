package com.dbms.scs.domain;

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
public class Honorarium {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer honorariumID;

    @Column
    private String guestFeedback;

    @Column
    private Double amount;

    @Column
    private String status;

    @Column
    private Long transactionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honored_for_id")
    private Event honoredFor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honored_to_guests_id")
    private GuestsSpeakers honoredToGuests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "honorarirum_responsibles_id")
    private SCSMembers honorarirumResponsibles;

}
