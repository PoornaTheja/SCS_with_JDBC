package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VenueDTO {

    private Integer venueID;

    @Size(max = 255)
    private String venueName;

    private Long capacity;

    @Size(max = 255)
    private String contactperson;

    private Long contactnumber;

    @Size(max = 255)
    private String location;

}
