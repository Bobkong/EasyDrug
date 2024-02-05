package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrugCode implements Serializable {
    @SerializedName("username")
    private String userName;
    @SerializedName("drug_upc_code")
    private String drugUpcCode;
    @SerializedName("curr_drug")
    private String currDrug;

    public DrugCode(String userName, String drugUpcCode, String currDrug) {
        this.userName = userName;
        this.drugUpcCode = drugUpcCode;
        this.currDrug = currDrug;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDrugUpcCode() {
        return drugUpcCode;
    }

    public void setDrugUpcCode(String drugUpcCode) {
        this.drugUpcCode = drugUpcCode;
    }
}
