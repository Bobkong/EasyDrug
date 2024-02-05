package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodInteractionDetail implements Serializable {

    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String msg;

    @SerializedName("interactions")
    ArrayList<FoodInteraction> interactions;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<FoodInteraction> getInteractions() {
        return interactions;
    }

    public void setInteractions(ArrayList<FoodInteraction> interactions) {
        this.interactions = interactions;
    }
}
