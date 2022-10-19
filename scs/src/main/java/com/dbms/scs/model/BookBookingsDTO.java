package com.dbms.scs.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class BookBookingsDTO {

    private Integer bookingid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTaken;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedReturn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    private Double personalRating;

    @Size(max = 255)
    private String status;

    private Integer studorderbooking;

    private Integer bookingForBook;

}
