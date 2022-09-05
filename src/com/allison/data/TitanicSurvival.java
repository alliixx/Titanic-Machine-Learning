package com.allison.data;

public class TitanicSurvival {
    public String sex;
    public String age;
    public String cls;
    public String survival;

    public TitanicSurvival (String sex, String age, String cls, String survival) {
        this.sex = sex;
        this.age = age;
        this.cls = cls;
        this.survival = survival;
    }

    public String toString() {
        return "sex: " + this.sex + ", age: " + this.age + ", class: " + this.cls + ", survival: " + this.survival + ", \n";
    }
}
