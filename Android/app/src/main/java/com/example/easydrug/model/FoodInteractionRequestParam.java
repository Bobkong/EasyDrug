package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodInteractionRequestParam implements Serializable {

    @SerializedName("username")
    String username;

    @SerializedName("food_list")
    ArrayList<String> foodList;

    public FoodInteractionRequestParam(String username, ArrayList<String> foodList) {
        this.username = username;
        this.foodList = foodList;
    }
}
