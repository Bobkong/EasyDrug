package com.example.easydrug.netservice.Api;

import io.reactivex.Observable;
import com.example.easydrug.model.DrugLookUpInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpcLookUpApi {

    //sign up
    @GET("/prod/trial//lookup")
    Observable<DrugLookUpInfo> loopUpUpc(
            @Query("upc") String upc
    );
}
