package com.training.one.task1.repository;

import com.training.one.task1.model.BabyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BabyRepository extends JpaRepository<BabyModel, Integer> {


}
