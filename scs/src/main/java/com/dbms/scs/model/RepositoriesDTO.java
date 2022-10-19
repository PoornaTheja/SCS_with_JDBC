package com.dbms.scs.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class RepositoriesDTO {

    private Integer repoID;

    private Integer year;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate compiledOn;

    @Size(max = 255)
    private String repoLink;

    private Integer studentCompilesrepos;

    private Integer verticalRepos;

}
