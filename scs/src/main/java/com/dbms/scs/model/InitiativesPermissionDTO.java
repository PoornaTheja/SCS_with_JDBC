package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InitiativesPermissionDTO {

    private Integer permissionID;

    @Size(max = 255)
    private String attestedBy;

    @Size(max = 255)
    private String grantedBy;

    private Integer memberseekinitiativepermission;

    private Integer initpermissgranted;

    private Integer initpermissattestedby;

}
