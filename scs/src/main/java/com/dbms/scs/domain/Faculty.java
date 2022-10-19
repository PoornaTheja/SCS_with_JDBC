package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Faculty {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer facultyId;

    @Column
    private String department;

    @Column
    private Long phoneNo;

    @Column
    private String emailId;

    @Column
    private String qualification;

    @Column
    private String name;

    @OneToMany(mappedBy = "permissiongrantedby")
    private Set<EventsPermission> permissiongrantedbyEventsPermissions;

    @OneToMany(mappedBy = "permissionattestedby")
    private Set<EventsPermission> permissionattestedbyEventsPermissions;

    @OneToMany(mappedBy = "initpermissgranted")
    private Set<InitiativesPermission> initpermissgrantedInitiativesPermissions;

    @OneToMany(mappedBy = "initpermissattestedby")
    private Set<InitiativesPermission> initpermissattestedbyInitiativesPermissions;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "facultycounselsverticals",
            joinColumns = @JoinColumn(name = "faculty_faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "vertical_verticalid")
    )
    private Set<Vertical> facultycounselsverticalsVerticals;

    @OneToOne(
            mappedBy = "iMgrpssupervisedbyFac",
            fetch = FetchType.LAZY
    )
    private IMGroups iMgrpssupervisedbyFac;

}
