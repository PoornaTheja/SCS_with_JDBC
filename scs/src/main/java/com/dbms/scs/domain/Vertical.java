package com.dbms.scs.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Vertical {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer verticalID;

    @Column
    private String nameOfVertical;

    @OneToMany(mappedBy = "verticalmemberbelongs")
    private Set<SCSMembers> verticalmemberbelongsSCSMemberss;

    @OneToMany(mappedBy = "verticalRepos")
    private Set<Repositories> verticalReposRepositoriess;

    @OneToOne
    @JoinColumn(name = "vertical_budgets_id")
    private YearlyBudgets verticalBudgets;

    @OneToMany(mappedBy = "applicationstovertical")
    private Set<Application> applicationstoverticalApplications;

    @ManyToMany(mappedBy = "facultycounselsverticalsVerticals")
    private Set<Faculty> facultycounselsverticalsFacultys;

    @OneToOne(
            mappedBy = "meetbelongtovertical",
            fetch = FetchType.LAZY
    )
    private Teammeetings meetbelongtovertical;

    @OneToMany(mappedBy = "applicnpartofvertical")
    private Set<Application> applicnpartofverticalApplications;

}
