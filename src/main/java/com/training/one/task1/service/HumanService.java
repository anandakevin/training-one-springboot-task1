package com.training.one.task1.service;

import com.training.one.task1.model.HumanModel;
import com.training.one.task1.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {

    @Autowired
    HumanRepository humanRepository;

    public HumanModel insert(HumanModel humanModel) {
        return humanRepository.save(humanModel);
    }

    public HumanModel update(HumanModel humanModel) {
        return humanRepository.save(humanModel);
    }

    public void delete(HumanModel humanModel) {
        humanRepository.deleteById(humanModel.getId());
    }

    public List<HumanModel> findAll() {
        return humanRepository.findAll();
    }


}
