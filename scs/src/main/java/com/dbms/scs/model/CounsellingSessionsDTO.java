package com.dbms.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class CounsellingSessionsDTO {

    private Integer sessionID;

    @Size(max = 255)
    private String feedback;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime toTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Integer counseledBy;

    private Integer counselsTo;

}
