package com.yami.studio.banana.customerapp.network;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class ApiClient {

    private Context context;

    public ApiClient(Context context){
        this.context = context;
        VolleySingleton.getInstance(context).getRequestQueue();
    }

    public LiveData<NetworkStatus> getData(String url){
        final MutableLiveData<NetworkStatus> live = new MutableLiveData<>();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        live.postValue(new NetworkStatus(true, response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        live.postValue(new NetworkStatus(false, error.getMessage()));
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(request);
        return live;
    }

}
