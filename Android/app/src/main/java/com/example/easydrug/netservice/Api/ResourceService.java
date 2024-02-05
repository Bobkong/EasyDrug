package com.example.easydrug.netservice.Api;

import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.ResourcesContent;
import com.example.easydrug.model.ResourcesResponse;
import com.example.easydrug.model.SignUserParam;
import com.example.easydrug.netservice.EasyDrugServiceManager;
import com.example.easydrug.netservice.HttpResultFunc;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ResourceService {

    private static ResourceService instance;

    public static synchronized ResourceService getInstance(){
        if(instance==null)
            instance=new ResourceService();
        return instance;
    }

    private final ResourceApi resourceApi= EasyDrugServiceManager.getInstance().create(ResourceApi.class);

    public Observable<ResourcesResponse> getResources(){
        return resourceApi.getResources()
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
