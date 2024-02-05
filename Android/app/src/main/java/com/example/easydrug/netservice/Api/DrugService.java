package com.example.easydrug.netservice.Api;

import com.example.easydrug.model.DrugDetail;
import com.example.easydrug.model.DrugDetailRequestParam;
import com.example.easydrug.model.FoodInteractionDetail;
import com.example.easydrug.model.FoodInteractionRequestParam;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.netservice.HttpResultFunc;
import com.example.easydrug.netservice.EasyDrugServiceManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import com.example.easydrug.model.DrugCode;
import com.example.easydrug.model.DrugInfo;
import com.example.easydrug.model.DrugList;
import com.example.easydrug.model.SignUserParam;

import java.util.ArrayList;

public class DrugService {
    private static DrugService instance;
    public static synchronized DrugService getInstance(){
        if(instance==null)
            instance=new DrugService();
        return instance;
    }

    private final DrugApi drugApi= EasyDrugServiceManager.getInstance().create(DrugApi.class);

    public Observable<DrugList> getDrugList(String username){
        return drugApi.getDrugList(new SignUserParam(username))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<GeneralResponse> addDrug(String username, String drugName, String drugImageUrl, String drugUpcCode, String drugDesc, ArrayList<ArrayList<String>> interactionPairs){
        return drugApi.addDrug(new DrugInfo(username, drugName, drugImageUrl == null ? "" : drugImageUrl, drugUpcCode, drugDesc, interactionPairs))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
    public Observable<GeneralResponse> removeDrug(String username, String drugUpcCode, String currDrug){
        return drugApi.removeDrug(new DrugCode(username, drugUpcCode, currDrug))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<DrugDetail> getDrugDetail(String username, String drugName, String drugDesc, String upcCode) {
        return drugApi.getDrugDetail(new DrugDetailRequestParam(username, drugName, drugDesc, upcCode))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<FoodInteractionDetail> getFoodInteractionDetail(String username, ArrayList<String> foodList) {
        return drugApi.getDFI(new FoodInteractionRequestParam(username, foodList))
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }


}
