package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AwardDTO {

    private Integer awardID;

    @Size(max = 255)
    private String awardType;

    @Size(max = 255)
    private String certificate;

    private Integer year;

}
