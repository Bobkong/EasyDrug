package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SideEffectPossibility implements Serializable {
    @SerializedName("side_effect_name")
    String sideEffectName;

    @SerializedName("probability")
    String possibility;

    @SerializedName("definition")
    String definition;

    public String getSideEffectName() {
        return sideEffectName;
    }

    public void setSideEffectName(String sideEffectName) {
        this.sideEffectName = sideEffectName;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public SideEffectPossibility(String sideEffectName, String definition) {
        this.sideEffectName = sideEffectName;
        this.definition = definition;
    }
}
