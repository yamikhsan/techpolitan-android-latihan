package com.yami.studio.banana.customerapp.activity.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yami.studio.banana.customerapp.BuildConfig;
import com.yami.studio.banana.customerapp.network.ApiClient;
import com.yami.studio.banana.customerapp.network.NetworkStatus;

public class DetailViewModel extends AndroidViewModel {

    private ApiClient client;
    private String url;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        url = BuildConfig.URL + BuildConfig.ID;
    }

    public LiveData<NetworkStatus> getProduct(long id){
        return client.getData(url + id);
    }

}
