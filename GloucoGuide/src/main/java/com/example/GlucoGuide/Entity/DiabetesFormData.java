package com.example.GlucoGuide.Entity;

import java.util.HashMap;
import java.util.Map;

public class DiabetesFormData {
    private int age;
    private int gender;
    private int polyuria;
    private int polydipsia;
    private int suddenWeightLoss;
    private int weakness;
    private int polyphagia;
    private int genitalThrush;
    private int visualBlurring;
    private int itching;
    private int irritability;
    private int delayedHealing;
    private int partialParesis;
    private int muscleStiffness;
    private int alopecia;
    private int obesity;


    public Map<String, Object> toMap() {
        Map<String, Object> formDataMap = new HashMap<>();
        formDataMap.put("Age", this.age);
        formDataMap.put("Gender", this.gender);
        formDataMap.put("Polyuria", this.polyuria);
        formDataMap.put("Polydipsia", this.polydipsia);
        formDataMap.put("SuddenWeightLoss", this.suddenWeightLoss);
        formDataMap.put("Weakness", this.weakness);
        formDataMap.put("Polyphagia", this.polyphagia);
        formDataMap.put("GenitalThrush", this.genitalThrush);
        formDataMap.put("VisualBlurring", this.visualBlurring);
        formDataMap.put("Itching", this.itching);
        formDataMap.put("Irritability", this.irritability);
        formDataMap.put("DelayedHealing", this.delayedHealing);
        formDataMap.put("PartialParesis", this.partialParesis);
        formDataMap.put("MuscleStiffness", this.muscleStiffness);
        formDataMap.put("Alopecia", this.alopecia);
        formDataMap.put("Obesity", this.obesity);

        return formDataMap;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPolyuria() {
        return polyuria;
    }

    public void setPolyuria(int polyuria) {
        this.polyuria = polyuria;
    }

    public int getPolydipsia() {
        return polydipsia;
    }

    public void setPolydipsia(int polydipsia) {
        this.polydipsia = polydipsia;
    }

    public int getSuddenWeightLoss() {
        return suddenWeightLoss;
    }

    public void setSuddenWeightLoss(int suddenWeightLoss) {
        this.suddenWeightLoss = suddenWeightLoss;
    }

    public int getWeakness() {
        return weakness;
    }

    public void setWeakness(int weakness) {
        this.weakness = weakness;
    }

    public int getPolyphagia() {
        return polyphagia;
    }

    public void setPolyphagia(int polyphagia) {
        this.polyphagia = polyphagia;
    }

    public int getGenitalThrush() {
        return genitalThrush;
    }

    public void setGenitalThrush(int genitalThrush) {
        this.genitalThrush = genitalThrush;
    }

    public int getVisualBlurring() {
        return visualBlurring;
    }

    public void setVisualBlurring(int visualBlurring) {
        this.visualBlurring = visualBlurring;
    }

    public int getItching() {
        return itching;
    }

    public void setItching(int itching) {
        this.itching = itching;
    }

    public int getIrritability() {
        return irritability;
    }

    public void setIrritability(int irritability) {
        this.irritability = irritability;
    }

    public int getDelayedHealing() {
        return delayedHealing;
    }

    public void setDelayedHealing(int delayedHealing) {
        this.delayedHealing = delayedHealing;
    }

    public int getPartialParesis() {
        return partialParesis;
    }

    public void setPartialParesis(int partialParesis) {
        this.partialParesis = partialParesis;
    }

    public int getMuscleStiffness() {
        return muscleStiffness;
    }

    public void setMuscleStiffness(int muscleStiffness) {
        this.muscleStiffness = muscleStiffness;
    }

    public int getAlopecia() {
        return alopecia;
    }

    public void setAlopecia(int alopecia) {
        this.alopecia = alopecia;
    }

    public int getObesity() {
        return obesity;
    }

    public void setObesity(int obesity) {
        this.obesity = obesity;
    }
}
