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
public class Counselor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counselorId;

    @Column
    private String name;

    @Column
    private String qualification;

    @Column
    private String designation;

    @Column
    private String dateOfJoining;

    @Column
    private String timings;

    @Column
    private String jobType;

    @Column
    private Long phoneNo;

    @Column
    private String emailId;

    @Column
    private String currentStatus;

    @OneToMany(mappedBy = "counseledBy")
    private Set<CounsellingSessions> counseledByCounsellingSessionss;

}
