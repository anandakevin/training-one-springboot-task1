package com.training.one.task1.controller;

import com.training.one.task1.repository.BabyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("baby")
public class BabyRestController {

    @Autowired
    private BabyRepository babyRepository;
}
