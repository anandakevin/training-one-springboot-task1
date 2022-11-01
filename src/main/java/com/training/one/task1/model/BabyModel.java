package com.training.one.task1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "baby")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BabyModel {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "human_id", referencedColumnName = "id")
    private HumanModel humanModel;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "human_id")
    private PregnantMotherModel pregnantMotherModel;

    @Id
    @Column(name = "human_id")
    private Integer humanId;

    @Column(name = "is_breast_milk")
    private Boolean isBreastMilk;

    @Column(name = "is_immunized")
    private Boolean isImmunized;

    @Column(name = "stunting_potention")
    private Integer stuntingPotention;
}
