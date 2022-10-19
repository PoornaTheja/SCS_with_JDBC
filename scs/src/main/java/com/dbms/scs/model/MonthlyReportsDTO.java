package com.dbms.scs.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class MonthlyReportsDTO {

    private Integer reportID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate compiledOn;

    @Size(max = 255)
    private String reportLink;

    private Integer studntcompilesreports;

}
