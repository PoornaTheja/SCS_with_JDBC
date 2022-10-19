package com.dbms.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class InititativesDTO {

    private Integer initiativesId;

    @Size(max = 255)
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timings;

    private Long noOfAttendees;

    @Size(max = 255)
    private String resources;

    @Size(max = 255)
    private String minutesOfMeeting;

    private Integer permissionforinitiative;

    private Integer initiatvehostedat;

}
