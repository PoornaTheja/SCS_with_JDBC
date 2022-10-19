package com.dbms.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class TeammeetingsDTO {

    private Integer meetingID;

    private Integer noOfAttendees;

    @Size(max = 255)
    private String kindOfMeeting;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime fromTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime toTime;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String resourcesLink;

    private Integer meetbelongtovertical;

}
