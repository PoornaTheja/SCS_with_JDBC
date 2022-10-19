package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HonorariumDTO {

    private Integer honorariumID;

    @Size(max = 255)
    private String guestFeedback;

    private Double amount;

    @Size(max = 255)
    private String status;

    private Long transactionNumber;

    private Integer honoredFor;

    private Integer honoredToGuests;

    private Integer honorarirumResponsibles;

}
