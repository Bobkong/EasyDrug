package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateProfileParam implements Serializable {

    @SerializedName("old_username")
    String oldUsername;

    @SerializedName("new_username")
    String newUsername;

    @SerializedName("new_password")
    String newPassword;

    public UpdateProfileParam(String oldUsername, String newUsername, String newPassword) {
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
        this.newPassword = newPassword;
    }
}
