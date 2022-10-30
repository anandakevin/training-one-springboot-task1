package com.training.one.task1.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pregnant_mother")
@Getter
@Setter
@NoArgsConstructor
public class PregnantMotherModel {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human", referencedColumnName = "id")
    @Column(name = "human_id")
    private HumanModel human;

    @Column(name = "is_smoking")
    private String isSmoking;

    @Column(name = "is_vitamin")
    private String isVitamin;

    @Column(name = "recommendation")
    private String recommendation;
}
