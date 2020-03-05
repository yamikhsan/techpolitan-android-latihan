package com.yami.studio.banana.merchantapp.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

class VolleySingleton {
    private static VolleySingleton instance;
    private static RequestQueue requestQueue;

    private VolleySingleton(RequestQueue reqQueue) {
        requestQueue = reqQueue;
    }

    static synchronized VolleySingleton getInstance(RequestQueue reqQueue) {
        if (instance == null) {
            instance = new VolleySingleton(reqQueue);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        return requestQueue;
    }

    <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
