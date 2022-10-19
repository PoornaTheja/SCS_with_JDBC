package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BooksDTO {

    private Integer bookID;

    @Size(max = 255)
    private String bookName;

    @Size(max = 255)
    private String author;

    private Integer totalNoOfCopies;

    private Integer availableCopies;

    @Size(max = 255)
    private String status;

    private Long totalReads;

    private Double bookRating;

}
