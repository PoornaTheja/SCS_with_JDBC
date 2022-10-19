package com.dbms.scs.model;

import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class GuestsSpeakersDTO {

    private Integer guestID;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String designation;

    private Long accountNumber;

    @Size(max = 255)
    private String resources;

    @Size(max = 255)
    private String emailID;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime phoneNumber;

    @Size(max = 255)
    private String qualification;

}
