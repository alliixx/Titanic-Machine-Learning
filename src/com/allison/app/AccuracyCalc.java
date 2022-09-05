package com.allison.app;

import com.allison.data.TitanicSurvival;
import com.allison.model.BaseModel;

import java.util.List;
import java.util.Map;

public class AccuracyCalc {

    public static double accuracy (List<TitanicSurvival> testList , BaseModel model){
        Map<String, Double> probMap = model.calculateProbability();

        double accuracy = 0;
        for(TitanicSurvival ts: testList) {
            String key = ts.sex + "_" + ts.age + "_" + ts.cls;
            if ("1".equals(ts.survival)){
                accuracy = accuracy + Math.log(probMap.get(key));
            }else{
                accuracy = accuracy + Math.log(1 - probMap.get(key));
            }
        }

        return accuracy;
    }
}
