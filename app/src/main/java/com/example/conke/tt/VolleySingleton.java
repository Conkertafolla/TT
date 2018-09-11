package com.example.conke.tt;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {


    private static VolleySingleton InstanciaVolley;
    private RequestQueue request;
    private static Context contexto;


    private VolleySingleton(Context context) {
        contexto = context;
        request = getRequestQueue();

    }

    public static synchronized  VolleySingleton getInstanciaVolley(Context context) {
        if (InstanciaVolley == null) {
            InstanciaVolley = new VolleySingleton (context);
        }
        return InstanciaVolley;
    }

    public RequestQueue getRequestQueue() {
        if (request == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            request = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return request;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
