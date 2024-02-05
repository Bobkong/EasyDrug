package com.example.easydrug;

public interface Configs {
    // Azure speech
    String speechSubscriptionKey = "3c92dac0e64443f1bf51c236948ab803";
    String serviceRegion = "eastus";


    // SharedPreference
    String userNameKey = "username";
    String passwordKey = "password";
    String ifSignedUpKey = "ifSignedUpKey";

    // network response
    int requestSuccess = 0;
    int requestFail = 1;

    // intent extra
    String drugDetailFromScene = "drugDetailFromScene";
}
