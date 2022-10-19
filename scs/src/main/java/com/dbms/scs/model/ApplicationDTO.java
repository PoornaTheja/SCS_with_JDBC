package com.dbms.scs.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class ApplicationDTO {

    private Long applicationID;

    @Size(max = 255)
    private String isSelected;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateofapplication;

    private Integer applicationstovertical;

    private Integer applicnforrecruitment;

    private Integer applicantbecomesmember;

    private Integer studapplies;

    private Integer applicnpartofvertical;

}
