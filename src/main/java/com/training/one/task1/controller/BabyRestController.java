package com.training.one.task1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.one.task1.model.BabyModel;
import com.training.one.task1.model.HumanModel;
import com.training.one.task1.model.BabyModel;
import com.training.one.task1.model.PregnantMotherModel;
import com.training.one.task1.repository.BabyRepository;
import com.training.one.task1.service.BabyService;
import com.training.one.task1.service.HumanService;
import com.training.one.task1.service.PregnantMotherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("baby")
public class BabyRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BabyService babyService;

    @Autowired
    private HumanService humanService;

    @Autowired
    private PregnantMotherService pregnantMotherService;

    ObjectMapper oMapper = new ObjectMapper();

    @GetMapping("/findAll")
    public Object findAll() {
        List<BabyModel> babyModelList = babyService.findAll();

        List<Object> map = new ArrayList<>();

        for (BabyModel obj : babyModelList) {
            LOGGER.info("babyModel {}", obj.toString());
            Map<String, Object> map2 = oMapper.convertValue(obj, Map.class);
            Integer stuntingPotentionScore = (Integer) map2.get("stuntingPotention");
            map2.replace("stuntingPotention", stuntingPotentionScore, determineStuntingPotentionString(stuntingPotentionScore));
            map.add(obj);
        }

        return map;
    }

    @GetMapping("/findById")
    public Object findById(@RequestParam("id") Integer id) {
        BabyModel babyModel = babyService.findById(id);
//        Map<Integer, Object> map = babyModel
//                .collect(Collectors.toMap(BabyModel::getHumanId, Function.identity()));

        Map<String, Object> map = oMapper.convertValue(babyModel, Map.class);
        Integer stuntingPotentionScore = (Integer) map.get("stuntingPotention");
        map.replace("stuntingPotention", stuntingPotentionScore, determineStuntingPotentionString(stuntingPotentionScore));

        return map;
    }

    @PostMapping("/insert")
    public BabyModel insert(@RequestBody Map payload) {
        try {
            HumanModel humanModel = new HumanModel();
            Map humanData = (Map) payload.get("human");
            humanModel.setName(humanData.get("name").toString());
            humanModel.setBirthdate(humanData.get("birthdate").toString());
            humanModel.setWeight(humanData.get("weight").toString());
            humanModel.setHeight(humanData.get("height").toString());
            humanModel = humanService.insert(humanModel);

            BabyModel babyModel = new BabyModel();
            babyModel.setHumanId(humanModel.getId());

            PregnantMotherModel pregnantMotherModel = pregnantMotherService.findById(Integer.valueOf(payload.get("parent_id").toString()));
            babyModel.setPregnantMotherModel(pregnantMotherModel);
            babyModel.setIsBreastMilk(Boolean.valueOf(payload.get("is_breast_milk").toString()));
            babyModel.setIsImmunized(Boolean.valueOf(payload.get("is_immunized").toString()));
            babyModel.setStuntingPotention(determineStuntingPotention(babyModel));
            babyModel = babyService.insert(babyModel);
//            babyModel.getHumanModel();
            return babyModel;
//            return babyService.insert(babyModel);

        } catch (Exception e) {
            LOGGER.error("Exception happened: " + e);
            return null;
        }
    }

    @PostMapping("/update")
    public Object update(@RequestBody Map payload) {
        try {

            if (payload.containsKey("human")) {

                HumanModel humanModel = null;
                BabyModel babyModel = null;
                Map humanData = (Map) payload.get("human");

                if (humanData.containsKey("id")) {
                    Integer humanId = (Integer) humanData.get("id");
                    humanModel = humanService.findById(humanId);
                } else if (humanData.containsKey("name")) {
                    String humanName = (String) humanData.get("name");
                    humanModel = humanService.findByName(humanName);
                }

                if (humanModel != null) {
                    babyModel = babyService.findById(humanModel.getId());

                    humanModel.setName(humanData.get("name").toString());
                    humanModel.setBirthdate(humanData.get("birthdate").toString());
                    humanModel.setWeight(humanData.get("weight").toString());
                    humanModel.setHeight(humanData.get("height").toString());
                    humanModel = humanService.update(humanModel);

                    babyModel.setHumanId(humanModel.getId());

                    PregnantMotherModel pregnantMotherModel = pregnantMotherService.findById(Integer.valueOf(payload.get("parent_id").toString()));
                    babyModel.setPregnantMotherModel(pregnantMotherModel);
                    babyModel.setIsBreastMilk(Boolean.valueOf(payload.get("is_breast_milk").toString()));
                    babyModel.setIsImmunized(Boolean.valueOf(payload.get("is_immunized").toString()));
                    babyModel.setStuntingPotention(determineStuntingPotention(babyModel));
                    return babyService.update(babyModel);
                } else return null;

            } else return null;

        } catch (Exception e) {
            LOGGER.error("Exception happened: " + e);
            return null;
        }
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody Map payload) {
        try {
            if (payload.containsKey("human_id")) {
                BabyModel babyModel = babyService.findById((Integer) payload.get("human_id"));
                babyService.delete(babyModel);
                return babyModel;
            } else if (payload.containsKey("human_name")) {
                HumanModel humanModel = humanService.findByName((String) payload.get("human_name"));
                BabyModel babyModel = babyService.findById(humanModel.getId());
                babyService.delete(babyModel);
                return babyModel;
            } else return "Invalid!";
        } catch (Exception e) {
            LOGGER.error("Exception happened: {}", e);
            return "Error occured!";
        }
    }

    public Integer determineStuntingPotention(BabyModel babyModel) {
        if (!babyModel.getIsBreastMilk() && !babyModel.getIsImmunized()) return 2;
        else if ((!babyModel.getIsBreastMilk() || !babyModel.getIsImmunized())) return 1;
        else if (babyModel.getIsBreastMilk() && babyModel.getIsImmunized()) return 0;
        else return -1;
    }

    public String determineStuntingPotentionString(Integer stuntingPotentionScore) {
        switch (stuntingPotentionScore) {
            case 0:
                return "Tidak Stunting";
            case 1:
                return "Stunting";
            case 2:
                return "Stunting Parah";
            case -1:
            default:
                return "Invalid";
        }
    }

}
