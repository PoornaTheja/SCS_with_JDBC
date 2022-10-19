package com.dbms.scs.domain;

import java.time.LocalTime;
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
public class GuestsSpeakers {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guestID;

    @Column
    private String name;

    @Column
    private String designation;

    @Column
    private Long accountNumber;

    @Column
    private String resources;

    @Column
    private String emailID;

    @Column
    private LocalTime phoneNumber;

    @Column
    private String qualification;

    @OneToMany(mappedBy = "honoredToGuests")
    private Set<Honorarium> honoredToGuestsHonorariums;

}
