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
public class EventsPermission {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permissionID;

    @Column
    private String attestedBy;

    @Column
    private String grantedBy;

    @OneToOne(mappedBy = "seekpermission", fetch = FetchType.LAZY)
    private Event seekpermission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissiongrantedby_id")
    private Faculty permissiongrantedby;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissionattestedby_id")
    private Faculty permissionattestedby;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membertakespermission_id")
    private SCSMembers membertakespermission;

}
