package com.example.easydrug.netservice.Api;

import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.ResourcesResponse;
import com.example.easydrug.model.SignUserParam;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ResourceApi {
    //get
    @GET("/getResources")
    Observable<ResourcesResponse> getResources(
        
    );
}
