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
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class MonthlyReports {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportID;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @Column
    private LocalDate compiledOn;

    @Column
    private String reportLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studntcompilesreports_id")
    private Student studntcompilesreports;

}
