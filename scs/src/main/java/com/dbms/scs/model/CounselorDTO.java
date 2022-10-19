package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CounselorDTO {

    private Integer counselorId;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String qualification;

    @Size(max = 255)
    private String designation;

    @Size(max = 255)
    private String dateOfJoining;

    @Size(max = 255)
    private String timings;

    @Size(max = 255)
    private String jobType;

    private Long phoneNo;

    @Size(max = 255)
    private String emailId;

    @Size(max = 255)
    private String currentStatus;

}
