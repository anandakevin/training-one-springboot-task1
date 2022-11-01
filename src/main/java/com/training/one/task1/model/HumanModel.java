package com.training.one.task1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "human")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HumanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "weight")
    private String weight;

    @Column(name = "height")
    private String height;

}
