package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InductionProgramDTO {

    private Integer inductionId;

    private Integer year;

    @Size(max = 255)
    private String duration;

    @Size(max = 255)
    private String chairman;

    private Integer noOfAttendees;

}
