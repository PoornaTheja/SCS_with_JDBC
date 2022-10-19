package com.dbms.scs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IMGroupsDTO {

    private Integer groupID;

    @JsonProperty("iMgroupsinprogram")
    private Integer iMgroupsinprogram;

    @JsonProperty("iMgrpssupervisedbyFac")
    private Integer iMgrpssupervisedbyFac;

}
