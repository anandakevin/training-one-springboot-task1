package com.training.one.task1.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pregnant_mother")
@Getter
@Setter
@NoArgsConstructor
public class PregnantMotherModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private HumanModel humanModel;

    @Id
    @Column(name = "human_id")
    private Integer humanId;

    @Column(name = "is_smoking")
    private Boolean isSmoking;

    @Column(name = "is_vitamin")
    private Boolean isVitamin;

    @Column(name = "recommendation")
    private Integer recommendation;
}
