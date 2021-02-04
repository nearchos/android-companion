package io.github.nearchos.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Nearchos Paspallis
 * Created: 16-Jan-21
 */
class SingletonRequestQueue {

    private static SingletonRequestQueue singletonRequestQueue = null;
    private static Context applicationContext;
    private RequestQueue requestQueue;

    private SingletonRequestQueue(final Context context) {
        applicationContext = context.getApplicationContext();
        this.requestQueue = getRequestQueue();
    }

    public static SingletonRequestQueue getInstance(final Context context) {
        if(singletonRequestQueue == null) {
            synchronized (SingletonRequestQueue.class) {
                if(singletonRequestQueue == null) {
                    singletonRequestQueue = new SingletonRequestQueue(context);
                }
            }
        }
        return singletonRequestQueue;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            synchronized (SingletonRequestQueue.class) {
                if(requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(applicationContext);
                }
            }
        }
        return requestQueue;
    }
}