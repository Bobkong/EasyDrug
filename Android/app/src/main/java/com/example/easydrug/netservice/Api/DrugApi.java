package com.example.easydrug.netservice.Api;

import io.reactivex.Observable;
import com.example.easydrug.model.DrugCode;
import com.example.easydrug.model.DrugDetail;
import com.example.easydrug.model.DrugDetailRequestParam;
import com.example.easydrug.model.DrugInfo;
import com.example.easydrug.model.DrugList;
import com.example.easydrug.model.FoodInteractionDetail;
import com.example.easydrug.model.FoodInteractionRequestParam;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.SignUserParam;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DrugApi {

    //sign up
    @POST("/getDrugList")
    Observable<DrugList> getDrugList(
            @Body SignUserParam username
    );

    //add drug
    @POST("/addDrug")
    Observable<GeneralResponse> addDrug(
            @Body DrugInfo params
    );

    //remove drug
    @POST("/removeDrug")
    Observable<GeneralResponse> removeDrug(
            @Body DrugCode params
    );

    //drug detail
    @POST("/getDrugDetail")
    Observable<DrugDetail> getDrugDetail(
            @Body DrugDetailRequestParam params
    );

    //drug detail
    @POST("/getDFI")
    Observable<FoodInteractionDetail> getDFI(
            @Body FoodInteractionRequestParam params
    );
}
