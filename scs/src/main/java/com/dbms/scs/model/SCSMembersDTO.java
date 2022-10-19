package com.dbms.scs.model;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class SCSMembersDTO {

    private Integer memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @Size(max = 255)
    private String currentPosition;

    private Integer verticalmemberbelongs;

    private Integer membersresponsblebills;

    private List<Integer> membersattendmeets;

    private Integer memberbecomesIM;

}
