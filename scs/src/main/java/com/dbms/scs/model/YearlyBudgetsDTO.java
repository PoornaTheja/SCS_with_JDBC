package com.dbms.scs.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class YearlyBudgetsDTO {

    private Integer verticalID;
    private Integer year;
    private Double totalAmount;
    private Double amountSpent;

}
