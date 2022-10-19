package com.dbms.scs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RecruitmentDTO {

    private Integer recruitmentID;

    private Integer year;

    @Size(max = 255)
    @JsonProperty("pOR")
    private String pOR;

}
