package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Vendors {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vendorID;

    @Column
    private String vendorName;

    @Column
    private String address;

    @Column
    private Long contactNo;

    @Column
    private String item;

    @OneToMany(mappedBy = "vendorbilledby")
    private Set<Bills> vendorbilledbyBillss;

}
