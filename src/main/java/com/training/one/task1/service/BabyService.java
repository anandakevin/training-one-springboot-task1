package com.training.one.task1.service;

import com.training.one.task1.model.BabyModel;
import com.training.one.task1.model.PregnantMotherModel;
import com.training.one.task1.repository.BabyRepository;
import com.training.one.task1.repository.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BabyService {

    @Autowired
    BabyRepository babyRepository;

    public BabyModel insert(BabyModel babyModel) {
        return babyRepository.save(babyModel);
    }

    public BabyModel update(BabyModel babyModel) {
        return babyRepository.save(babyModel);
    }

    public void delete(BabyModel babyModel) {
        babyRepository.deleteById(babyModel.getHumanModel().getId());
    }

    public List<BabyModel> findAll() {
        return babyRepository.findAll();
    }

    public BabyModel findById(Integer id) {
        return babyRepository.findById(id).get();
    }

}
