package com.example.easydrug.netservice.Api;


import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.SendFeedbackParam;
import com.example.easydrug.model.UpdateProfileParam;
import com.example.easydrug.netservice.HttpResultFunc;
import com.example.easydrug.netservice.EasyDrugServiceManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import com.example.easydrug.model.SignUserParam;

public class SignService {
    private static SignService instance;
    public static synchronized SignService getInstance(){
        if(instance==null)
            instance=new SignService();
        return instance;
    }

    private final SignApi signApi= EasyDrugServiceManager.getInstance().create(SignApi.class);

    public Observable<GeneralResponse> signUp(String username, String password){
        return signApi.signUp(new SignUserParam(username, password))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<GeneralResponse> signIn(String username, String password){
        return signApi.signIn(new SignUserParam(username, password))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<GeneralResponse> updateProfile(String oldUsername, String newUsername, String newPassword){
        return signApi.updateProfile(new UpdateProfileParam(oldUsername, newUsername, newPassword))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<GeneralResponse> sendFeedback(String username, String email, String title, String content){
        return signApi.sendFeedback(new SendFeedbackParam(username, email, title, content))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
