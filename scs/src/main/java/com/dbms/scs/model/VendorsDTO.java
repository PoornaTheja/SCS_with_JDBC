package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VendorsDTO {

    private Integer vendorID;

    @Size(max = 255)
    private String vendorName;

    @Size(max = 255)
    private String address;

    private Long contactNo;

    @Size(max = 255)
    private String item;

}
