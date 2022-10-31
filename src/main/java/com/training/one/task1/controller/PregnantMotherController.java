package com.training.one.task1.controller;

import com.training.one.task1.repository.PregnantMotherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pregnantmother")
public class PregnantMotherController {

    @Autowired
    private PregnantMotherRepository pregnantMotherRepository;

}
