package com.dbms.scs.model;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacultyDTO {

    private Integer facultyId;

    @Size(max = 255)
    private String department;

    private Long phoneNo;

    @Size(max = 255)
    private String emailId;

    @Size(max = 255)
    private String qualification;

    @Size(max = 255)
    private String name;

    private List<Integer> facultycounselsverticalss;

}
