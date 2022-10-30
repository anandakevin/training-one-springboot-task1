package com.training.one.task1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "baby")
@Getter
@Setter
@NoArgsConstructor
public class BabyModel {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human", referencedColumnName = "id")
    @Column(name = "human_id")
    private HumanModel human;

    @Column(name = "is_breast_milk")
    private String isBreastMilk;

    @Column(name = "is_immunized")
    private String isImmunized;

    @Column(name = "stunting_pontention")
    private String stuntingPontention;
}
