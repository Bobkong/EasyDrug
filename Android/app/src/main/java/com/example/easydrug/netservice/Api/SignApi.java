package com.example.easydrug.netservice.Api;


import io.reactivex.Observable;

import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.SendFeedbackParam;
import com.example.easydrug.model.SignUserParam;
import com.example.easydrug.model.UpdateProfileParam;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignApi {
    //sign up
    @POST("/signup")
    Observable<GeneralResponse> signUp(
            @Body SignUserParam params
            );


    //sign in
    @POST("/login")
    Observable<GeneralResponse> signIn(
            @Body SignUserParam params
            );

    //sign in
    @POST("/updateProfile")
    Observable<GeneralResponse> updateProfile(
            @Body UpdateProfileParam params
    );

    @POST("/sendFeedback")
    Observable<GeneralResponse> sendFeedback(
            @Body SendFeedbackParam params
    );
}
