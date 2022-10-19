package com.dbms.scs.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class BillsDTO {

    private Integer billID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBilling;

    @Size(max = 255)
    private String purpose;

    @Size(max = 255)
    private String status;

    private Integer vendorbilledby;

}
