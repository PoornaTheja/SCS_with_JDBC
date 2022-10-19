package com.dbms.scs.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class BookBookings {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingid;

    @Column
    private LocalDate dateTaken;

    @Column
    private LocalDate expectedReturn;

    @Column
    private LocalDate returnDate;

    @Column
    private Double personalRating;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studorderbooking_id")
    private Student studorderbooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_for_book_id")
    private Books bookingForBook;

}
