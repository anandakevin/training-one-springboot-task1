package com.training.one.task1.repository;

import com.training.one.task1.model.HumanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HumanRepository extends JpaRepository<HumanModel, Integer> {

    List<HumanModel> findAllByNameOrderByName(String name);
}
