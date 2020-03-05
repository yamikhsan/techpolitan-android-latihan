package com.yami.studio.banana.customerapp.activity.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yami.studio.banana.customerapp.BuildConfig;
import com.yami.studio.banana.customerapp.network.ApiClient;
import com.yami.studio.banana.customerapp.network.NetworkStatus;

public class MainViewModel extends AndroidViewModel {

    private ApiClient client;
    private String url;

    public MainViewModel(@NonNull Application application) {
        super(application);
        client = new ApiClient(application);
        url = BuildConfig.URL + BuildConfig.PRODUCTS;
    }

    public LiveData<NetworkStatus> getProductData(){
        return client.getData(url);
    }
}
