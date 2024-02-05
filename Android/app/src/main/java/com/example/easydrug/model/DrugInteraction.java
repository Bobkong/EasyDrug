package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DrugInteraction implements Serializable {

    @SerializedName("other_drug_name")
    String drugName;

    @SerializedName("interaction_desc")
    String interactionDesc;

    @SerializedName("probability")
    String probability;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getInteractionDesc() {
        return interactionDesc;
    }

    public void setInteractionDesc(String interactionDesc) {
        this.interactionDesc = interactionDesc;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public DrugInteraction(String drugName, String interactionDesc, String probability) {
        this.drugName = drugName;
        this.interactionDesc = interactionDesc;
        this.probability = probability;
    }
}
