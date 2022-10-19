package com.dbms.scs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class InitiativesPermission {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionID;

    @Column
    private String attestedBy;

    @Column
    private String grantedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberseekinitiativepermission_id")
    private SCSMembers memberseekinitiativepermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initpermissgranted_id")
    private Faculty initpermissgranted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initpermissattestedby_id")
    private Faculty initpermissattestedby;

    @OneToOne(
            mappedBy = "permissionforinitiative",
            fetch = FetchType.LAZY
    )
    private Inititatives permissionforinitiative;

}
