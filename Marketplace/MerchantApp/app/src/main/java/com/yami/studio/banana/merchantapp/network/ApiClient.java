package com.yami.studio.banana.merchantapp.network;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {

    private RequestQueue requestQueue;

    public ApiClient(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public LiveData<NetworkStatus> call(
            int method,
            String url,
            @Nullable JSONObject json,
            @Nullable final HashMap<String, String> header,
            @Nullable final HashMap<String, String> body
            ){
        final MutableLiveData<NetworkStatus> live = new MutableLiveData<>();

        JsonObjectRequest request = new JsonObjectRequest(
                method,
                url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        live.postValue(new NetworkStatus(true, response.toString()));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkStatus networkStatus = new NetworkStatus(false);
                        networkStatus.setVolleyError(error);
                        live.postValue(networkStatus);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                return header == null ? new HashMap<String, String>() : header;
            }

            @Override
            protected Map<String, String> getParams() {
                return body == null ? new HashMap<String, String>() : body;
            }
        };
        VolleySingleton.getInstance(requestQueue).addToRequestQueue(request);
        return live;
    }
}
