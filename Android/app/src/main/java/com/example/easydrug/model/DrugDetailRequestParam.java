package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugDetailRequestParam implements Serializable {
    @SerializedName("username")
    String userName;

    @SerializedName("curr_drug")
    String currDrug;

    @SerializedName("drug_desc")
    String drugDesc;

    @SerializedName("upc_code")
    String upcCode;

    public DrugDetailRequestParam(String userName, String currDrug, String drugDesc, String upcCode) {
        this.userName = userName;
        this.currDrug = currDrug;
        this.drugDesc = drugDesc;
        this.upcCode = upcCode;
    }
}
