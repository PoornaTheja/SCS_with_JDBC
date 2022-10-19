package com.dbms.scs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class StudentDTO {

    private Integer studentRollNo;

    @Size(max = 255)
    private String name;

    private Integer yearOfjoin;

    @Size(max = 255)
    private String department;

    private Integer yearOfGradn;

    @Size(max = 255)
    private String program;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Long phoneNumber;

    @Size(max = 255)
    @JsonProperty("eMailId")
    private String eMailId;

    private Integer studentPassedOut;

    @JsonProperty("sCSMembership")
    private Integer sCSMembership;

    private List<Integer> studparticipateevents;

    private List<Integer> studentawardeds;

    private List<Integer> studinitiativeparts;

    private Integer studspartofIMgp;

}
