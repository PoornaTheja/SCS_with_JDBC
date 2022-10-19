package com.dbms.scs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InductionmentorDTO {

    private Integer mentorID;

    private Integer nodalMentorID;

    @JsonProperty("iMmentoredbyNM")
    private Integer iMmentoredbyNM;

    @JsonProperty("iMpartofInduction")
    private Integer iMpartofInduction;

    @JsonProperty("iMGrpcontainsIM")
    private Integer iMGrpcontainsIM;

}
