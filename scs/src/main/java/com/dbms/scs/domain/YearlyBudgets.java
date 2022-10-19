package com.dbms.scs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class YearlyBudgets {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer verticalID;

    @Column
    private Integer year;

    @Column
    private Double totalAmount;

    @Column
    private Double amountSpent;

    @OneToOne(
            mappedBy = "verticalBudgets",
            fetch = FetchType.LAZY
    )
    private Vertical verticalBudgets;

}
