package com.dbms.scs.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VerticalDTO {

    private Integer verticalID;

    @Size(max = 255)
    private String nameOfVertical;

    private Integer verticalBudgets;

}
