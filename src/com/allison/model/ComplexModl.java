package com.allison.model;

import com.allison.data.TitanicSurvival;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.allison.app.AppConstants.variables;

public class ComplexModl extends BaseModel {

    public ComplexModl(List<TitanicSurvival> data) {
        super(data);
    }


    public Map<String, Double> calculate(boolean isSurvive, double count) {
        Map<String, Double> map = new HashMap<String, Double>();
        for (String key: variables){
            map.put(key, 0d);
        }

        String surviveStr = isSurvive ?  "1" : "0";

        for (TitanicSurvival ts: data){
           String key = ts.sex + "_" + ts.age + "_" + ts.cls;
           Double value = map.get(key);
           if (ts.survival.equals(surviveStr)) {
               value += 1;
           }
            map.put(key, value);
        }

        for (String key: variables)  {
            Double value = map.get(key);
            Double prob = (value+1) / (count+18);
            map.put(key, prob);
        }

        return map;
    }
}
