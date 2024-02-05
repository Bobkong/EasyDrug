package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendFeedbackParam implements Serializable {
    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("title")
    String title;

    @SerializedName("content")
    String content;

    public SendFeedbackParam(String username, String email, String title, String content) {
        this.username = username;
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
