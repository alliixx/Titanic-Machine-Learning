package com.allison.data;

public class AccuracyData {
    public Integer trainSize;
    public Double complexAvg;
    public Double complexStd;
    public Double naiveAvg;
    public Double naiveStd;

    public AccuracyData(Integer trainSize, Double complexAvg, Double complexStd, Double naiveAvg, Double naiveStd) {
        this.trainSize = trainSize;
        this.complexAvg = complexAvg;
        this.complexStd = complexStd;
        this.naiveAvg = naiveAvg;
        this.naiveStd = naiveStd;
    }

    public String toString() {
        return this.trainSize +", " + this.complexAvg + ", " + this.complexStd + ", " + this.naiveAvg
                + ", " + this.naiveStd + "\n";
    }
}
