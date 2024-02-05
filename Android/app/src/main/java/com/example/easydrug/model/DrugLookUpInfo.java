package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DrugLookUpInfo implements Serializable {
    @SerializedName("items")
    private ArrayList<DrugLookUpItem> items;

    public ArrayList<DrugLookUpItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DrugLookUpItem> items) {
        this.items = items;
    }
}
