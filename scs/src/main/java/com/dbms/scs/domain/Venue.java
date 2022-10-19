package com.dbms.scs.domain;

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
public class Venue {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer venueID;

    @Column
    private String venueName;

    @Column
    private Long capacity;

    @Column
    private String contactperson;

    @Column
    private Long contactnumber;

    @Column
    private String location;

    @OneToMany(mappedBy = "venuedAt")
    private Set<Event> venuedAtEvents;

    @OneToMany(mappedBy = "initiatvehostedat")
    private Set<Inititatives> initiatvehostedatInititativess;

}
