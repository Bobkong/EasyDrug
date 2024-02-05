package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodInteraction implements Serializable {

    @SerializedName("food_name")
    String foodName;

    @SerializedName("drug_interactions")
    ArrayList<DrugInteraction> drugInteractions;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<DrugInteraction> getDrugInteractions() {
        return drugInteractions;
    }

    public void setDrugInteractions(ArrayList<DrugInteraction> drugInteractions) {
        this.drugInteractions = drugInteractions;
    }
}
