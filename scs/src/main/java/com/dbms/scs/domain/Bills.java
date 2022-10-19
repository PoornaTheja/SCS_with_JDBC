package com.dbms.scs.domain;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Bills {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billID;

    @Column
    private LocalDate dateOfBilling;

    @Column
    private String purpose;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendorbilledby_id")
    private Vendors vendorbilledby;

    @OneToMany(mappedBy = "membersresponsblebills")
    private Set<SCSMembers> membersresponsblebillsSCSMemberss;

}
