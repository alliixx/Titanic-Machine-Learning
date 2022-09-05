package com.allison.model;

import com.allison.data.TitanicSurvival;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.allison.app.AppConstants.variables;

public abstract class BaseModel {
    protected List<TitanicSurvival> data;

    public BaseModel(List<TitanicSurvival> data) {
        this.data = data;
    }

    public Map<String, Double> calculateProbability() {
        double survivalCount = 0;
        double deathCount =  0;
        double total = 0;
        for (TitanicSurvival ts: data){
            if (ts.survival.equals("1")) {
                survivalCount++;
            }else{
                deathCount++;
            }
            total++;
        }
        Double sProb = (survivalCount+1)/(total+2);
        Double dProb = (deathCount+1)/(total+2);

        Map<String, Double> sMap = calculate(true, survivalCount);
        Map<String, Double> dMap = calculate(false, deathCount);

        Map<String, Double> map = new HashMap<String, Double>();
        for (String key: variables){
            Double sVal = sMap.get(key);
            Double dVal = dMap.get(key);
            Double finalProb = (sProb*sVal) / ((sProb*sVal) + (dProb*dVal));
            map.put(key, finalProb);
        }

        return map;
    }
    public abstract Map<String, Double> calculate(boolean isSurvive, double count);
}
