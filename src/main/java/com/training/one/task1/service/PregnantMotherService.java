package com.training.one.task1.service;

import com.training.one.task1.model.PregnantMotherModel;
import com.training.one.task1.model.HumanModel;
import com.training.one.task1.repository.PregnantMotherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PregnantMotherService {

    @Autowired
    PregnantMotherRepository pregnantMotherRepository;

    public PregnantMotherModel insert(PregnantMotherModel pregnantMotherModel) {
        return pregnantMotherRepository.save(pregnantMotherModel);
    }

    public PregnantMotherModel update(PregnantMotherModel pregnantMotherModel) {
        return pregnantMotherRepository.save(pregnantMotherModel);
    }

    public void delete(PregnantMotherModel pregnantMotherModel) {
        pregnantMotherRepository.deleteById(pregnantMotherModel.getHumanModel().getId());
//        pregnantMotherRepository.deleteById(pregnantMotherModel.getHumanId());
    }

    public List<PregnantMotherModel> findAll() {
        return pregnantMotherRepository.findAll();
    }

}
