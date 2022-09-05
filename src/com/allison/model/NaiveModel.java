package com.allison.model;

import com.allison.data.TitanicSurvival;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.allison.app.AppConstants.variables;

public class NaiveModel extends BaseModel {

    public NaiveModel(List<TitanicSurvival> data) {
        super(data);
    }


    private void calcAttributes(String key, boolean isAdd, Map<String, Double> inMap) {
        Double value = inMap.get(key);
        if (isAdd) {
            value += 1;
        }
        inMap.put(key, value);
    }
    public Map<String, Double> calculate(boolean isSurvive, double count) {
        Map<String, Double> map = new HashMap<String, Double>();

        String surviveStr = isSurvive ?  "1" : "0";

        Map<String, Double> sexMap = new HashMap<String, Double>();
        sexMap.put("male", 0d);
        sexMap.put("female", 0d);
        for (TitanicSurvival ts: data) {
            calcAttributes(ts.sex, ts.survival.equals(surviveStr), sexMap);
        }

        Map<String, Double> ageMap = new HashMap<String, Double>();
        ageMap.put("young", 0d);
        ageMap.put("middle", 0d);
        ageMap.put("old", 0d);
        for (TitanicSurvival ts: data) {
            calcAttributes(ts.age, ts.survival.equals(surviveStr), ageMap);
        }

        Map<String, Double> clsMap = new HashMap<String, Double>();
        clsMap.put("1", 0d);
        clsMap.put("2", 0d);
        clsMap.put("3", 0d);
        for (TitanicSurvival ts: data) {
            calcAttributes(ts.cls, ts.survival.equals(surviveStr), clsMap);
        }

        for (String key: variables)  {
            String[] keys  = key.split("_");
            Double prob = (sexMap.get(keys[0]) + 1) / (count+2) * (ageMap.get(keys[1]) + 1) / (count + 3) *
                    (clsMap.get(keys[2])+1) / (count + 3);
            map.put(key, prob);
        }

        return map;
    }
}

