package com.training.one.task1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.one.task1.model.HumanModel;
import com.training.one.task1.model.PregnantMotherModel;
import com.training.one.task1.service.HumanService;
import com.training.one.task1.service.PregnantMotherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("pregnantmother")
public class PregnantMotherRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PregnantMotherService pregnantMotherService;

    @Autowired
    private HumanService humanService;

    ObjectMapper oMapper = new ObjectMapper();

    @GetMapping("/findAll")
    public Object findAll() {
        List<PregnantMotherModel> pregnantMotherModelList = pregnantMotherService.findAll();

        List<Object> map = new ArrayList<>();

        for (PregnantMotherModel obj : pregnantMotherModelList) {
            Map<String, Object> map2 = oMapper.convertValue(obj, Map.class);
            Integer stuntingPotentionScore = (Integer) map2.get("stunting_potention");
            map2.replace("stunting_potention", stuntingPotentionScore, determineRecommendationString(stuntingPotentionScore));
            map.add(obj);
        }

        return map;
    }

    @GetMapping("/findById")
    public Object findById(@RequestParam("id") Integer id) {
        PregnantMotherModel pregnantMotherModel = pregnantMotherService.findById(id);
//        Map<Integer, Object> map = pregnantMotherModel
//                .collect(Collectors.toMap(PregnantMotherModel::getHumanId, Function.identity()));

        Map<String, Object> map = oMapper.convertValue(pregnantMotherModel, Map.class);
        Integer stuntingPotentionScore = (Integer) map.get("stunting_potention");
        map.replace("stunting_potention", stuntingPotentionScore, determineRecommendationString(stuntingPotentionScore));

        return map;
    }

    @PostMapping("/insert")
    public PregnantMotherModel insert(@RequestBody Map payload) {
        try {
            HumanModel humanModel = new HumanModel();
            Map humanData = (Map) payload.get("human");
            humanModel.setName(humanData.get("name").toString());
            humanModel.setBirthdate(humanData.get("birthdate").toString());
            humanModel.setWeight(humanData.get("weight").toString());
            humanModel.setHeight(humanData.get("height").toString());
            humanModel = humanService.insert(humanModel);

            PregnantMotherModel pregnantMotherModel = new PregnantMotherModel();
            pregnantMotherModel.setHumanId(humanModel.getId());
//            pregnantMotherModel.setHumanModel(humanModel);
            pregnantMotherModel.setIsSmoking(Boolean.valueOf(payload.get("is_smoking").toString()));
            pregnantMotherModel.setIsVitamin(Boolean.valueOf(payload.get("is_vitamin").toString()));

            pregnantMotherModel.setRecommendation(determineRecommendation(pregnantMotherModel));
            pregnantMotherModel = pregnantMotherService.insert(pregnantMotherModel);
//            pregnantMotherModel.getHumanModel();
            return pregnantMotherModel;
//            return pregnantMotherService.insert(pregnantMotherModel);

        } catch (Exception e) {
            LOGGER.error("Exception happened: " + e);
            return null;
        }
    }

    @PostMapping("/update")
    public PregnantMotherModel update(@RequestBody Map payload) {
        try {

            if (payload.containsKey("human")) {

                HumanModel humanModel = null;
                PregnantMotherModel pregnantMotherModel = null;
                Map humanData = (Map) payload.get("human");

                if (humanData.containsKey("id")) {
                    Integer humanId = (Integer) humanData.get("id");
                    humanModel = humanService.findById(humanId);
                } else if (humanData.containsKey("name")) {
                    String humanName = (String) humanData.get("name");
                    humanModel = humanService.findByName(humanName);
                }

                if (humanModel != null) {
                    pregnantMotherModel = pregnantMotherService.findById(humanModel.getId());

                    humanModel.setName(humanData.get("name").toString());
                    humanModel.setBirthdate(humanData.get("birthdate").toString());
                    humanModel.setWeight(humanData.get("weight").toString());
                    humanModel.setHeight(humanData.get("height").toString());
                    humanModel = humanService.update(humanModel);

                    pregnantMotherModel.setHumanId(humanModel.getId());
                    pregnantMotherModel.setIsSmoking(Boolean.valueOf(payload.get("is_smoking").toString()));
                    pregnantMotherModel.setIsVitamin(Boolean.valueOf(payload.get("is_vitamin").toString()));
                    pregnantMotherModel.setRecommendation(determineRecommendation(pregnantMotherModel));
                    return pregnantMotherService.update(pregnantMotherModel);
                } else return null;

            } else return null;

        } catch (Exception e) {
            LOGGER.error("Exception happened: " + e);
            return null;
        }
    }

    @PostMapping("/delete")
    public PregnantMotherModel delete(@RequestBody Map payload) {
        try {
            if (payload.containsKey("human_id")) {
                PregnantMotherModel pregnantMotherModel = pregnantMotherService.findById((Integer) payload.get("human_id"));
                pregnantMotherService.delete(pregnantMotherModel);
                return pregnantMotherModel;
            } else if (payload.containsKey("human_name")) {
                HumanModel humanModel = humanService.findByName((String) payload.get("human_name"));
                PregnantMotherModel pregnantMotherModel = pregnantMotherService.findById(humanModel.getId());
                pregnantMotherService.delete(pregnantMotherModel);
                return pregnantMotherModel;
            } else return null;
        } catch (Exception e) {
            LOGGER.error("Exception happened: " + e);
            return null;
        }
    }

    public Integer determineRecommendation(PregnantMotherModel pregnantMotherModel) {
        if (pregnantMotherModel.getIsSmoking() && !pregnantMotherModel.getIsVitamin()) return 2;
        else if ((pregnantMotherModel.getIsSmoking() && pregnantMotherModel.getIsVitamin())
                || (!pregnantMotherModel.getIsSmoking() && !pregnantMotherModel.getIsVitamin()))
            return 1;
        else if (!pregnantMotherModel.getIsSmoking() && pregnantMotherModel.getIsVitamin()) return 0;
        else return -1;
    }

    public String determineRecommendationString(Integer stuntingPotentionScore) {
        switch (stuntingPotentionScore) {
            case 0:
                return "Baik";
            case 1:
                return "Waspada";
            case 2:
                return "Awas";
            case -1:
            default:
                return "Invalid";
        }
    }

}
