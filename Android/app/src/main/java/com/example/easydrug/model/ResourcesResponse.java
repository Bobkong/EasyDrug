package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ResourcesResponse implements Serializable {
    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String msg;

    @SerializedName("tags")
    ArrayList<String> tag_list;

    @SerializedName("resources")
    ArrayList<ResourcesContent> resource_list;

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

    public ArrayList<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(ArrayList<String> tag_list) {
        this.tag_list = tag_list;
    }

    public ArrayList<ResourcesContent> getResource_list() {
        return resource_list;
    }

    public void setResource_list(ArrayList<ResourcesContent> resource_list) {
        this.resource_list = resource_list;
    }

}
