package com.dbms.scs.domain;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class CounsellingSessions {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionID;

    @Column
    private String feedback;

    @Column
    private LocalTime toTime;

    @Column
    private LocalTime fromTime;

    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counseled_by_id")
    private Counselor counseledBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counsels_to_id")
    private Student counselsTo;

}
