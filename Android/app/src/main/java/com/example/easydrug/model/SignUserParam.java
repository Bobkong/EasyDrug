package com.example.easydrug.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SignUserParam implements Serializable {
	@SerializedName("username")
	String username;
	@SerializedName("password")
	String password;

	public SignUserParam(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public SignUserParam(String username) {
		this.username = username;
	}
}

