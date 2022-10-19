package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventsPermissionDTO {

    private Integer permissionID;

    @Size(max = 255)
    private String attestedBy;

    @Size(max = 255)
    private String grantedBy;

    private Integer permissiongrantedby;

    private Integer permissionattestedby;

    private Integer membertakespermission;

}
