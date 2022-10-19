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
public class Books {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookID;

    @Column
    private String bookName;

    @Column
    private String author;

    @Column
    private Integer totalNoOfCopies;

    @Column
    private Integer availableCopies;

    @Column
    private String status;

    @Column
    private Long totalReads;

    @Column
    private Double bookRating;

    @OneToMany(mappedBy = "bookingForBook")
    private Set<BookBookings> bookingForBookBookBookingss;

}
