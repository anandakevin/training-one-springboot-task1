package com.training.one.task1.repository;

import com.training.one.task1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findByUsername(String username);
}
