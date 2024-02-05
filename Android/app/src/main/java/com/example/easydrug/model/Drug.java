package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Drug implements Serializable {
    @SerializedName("drug_image_url")
    private String drugImageUrl;
    @SerializedName("drug_name")
    private String drugName;
    @SerializedName("upc_code")
    private String upcCode;
    @SerializedName("drug_desc")
    private String drugDesc;
    @SerializedName("num_of_interactions")
    private int numberOfInteractions;

    public Drug(String drugImageUrl, String drugName, String upcCode, String drugDesc) {
        this.drugImageUrl = drugImageUrl;
        this.drugName = drugName;
        this.upcCode = upcCode;
        this.drugDesc = drugDesc;
    }

    public String getDrugImageUrl() {
        return drugImageUrl;
    }

    public void setDrugImageUrl(String drugImageUrl) {
        this.drugImageUrl = drugImageUrl;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public int getNumberOfInteractions() {
        return numberOfInteractions;
    }

    public void setNumberOfInteractions(int numberOfInteractions) {
        this.numberOfInteractions = numberOfInteractions;
    }
}
