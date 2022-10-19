package com.dbms.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class EventDTO {

    private Integer eventID;

    @Size(max = 255)
    private String resources;

    @Size(max = 255)
    private String minutesofMeeting;

    private Integer noOfAttendees;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime fromTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime toTime;

    private Integer venuedAt;

    private Integer seekpermission;

}
