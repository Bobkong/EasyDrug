package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DrugInfo implements Serializable {
    @SerializedName("username")
    private String userName;
    @SerializedName("drug_name")
    private String drugName;
    @SerializedName("drug_image_url")
    private String drugImageUrl;
    @SerializedName("drug_upc_code")
    private String drugUpcCode;
    @SerializedName("drug_desc")
    private String drugDesc;
    @SerializedName("interaction_pairs")
    ArrayList<ArrayList<String>> interactionPairs;

    public DrugInfo(String userName, String drugName, String drugImageUrl, String drugUpcCode, String drugDesc, ArrayList<ArrayList<String>> interactionPairs) {
        this.userName = userName;
        this.drugName = drugName;
        if (drugImageUrl == null) {
            this.drugImageUrl = "";
        } else {
            this.drugImageUrl = drugImageUrl;
        }
        this.drugUpcCode = drugUpcCode;
        if (drugDesc == null) {
            this.drugDesc = "";
        } else {
            this.drugDesc = drugDesc;
        }
        if (interactionPairs == null) {
            interactionPairs = new ArrayList<>();
        }
        this.interactionPairs = interactionPairs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugImageUrl() {
        return drugImageUrl;
    }

    public void setDrugImageUrl(String drugImageUrl) {
        this.drugImageUrl = drugImageUrl;
    }

    public String getDrugUpcCode() {
        return drugUpcCode;
    }

    public void setDrugUpcCode(String drugUpcCode) {
        this.drugUpcCode = drugUpcCode;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }
}
