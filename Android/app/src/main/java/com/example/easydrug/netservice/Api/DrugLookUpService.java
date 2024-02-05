package com.example.easydrug.netservice.Api;

import com.example.easydrug.netservice.DrugUpcServiceManager;
import com.example.easydrug.netservice.HttpResultFunc;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import com.example.easydrug.model.DrugLookUpInfo;

public class DrugLookUpService {

    private static DrugLookUpService instance;
    public static synchronized DrugLookUpService getInstance(){
        if(instance==null)
            instance=new DrugLookUpService();
        return instance;
    }

    private final UpcLookUpApi lookUpApi= DrugUpcServiceManager.getInstance().create(UpcLookUpApi.class);

    public Observable<DrugLookUpInfo> drugLookUp(String upc){
        return lookUpApi.loopUpUpc(upc)
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
