package io.github.nearchos.archcomp;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import io.github.nearchos.archcomp.github_api.GithubFollower;

import static io.github.nearchos.archcomp.MainActivity.TAG;

public class NetworkPolling implements LifecycleObserver {

    public static final String URL = "https://api.github.com/users/octocat/followers";
    public static final long DELAY = 1000L;
    public static final long PERIOD = 5000L;

    private Timer timer = new Timer();

    private RequestQueue requestQueue;

    private Gson gson = new Gson();

    private final StringRequest pollIssNowRequest = new StringRequest(
            Request.Method.GET,
            URL,
            this::handleResponse,
            this::handleError
    );

    private AppDao appDao;

    public NetworkPolling(final AppCompatActivity appCompatActivity) {
        this.appDao = AppDatabase.getDatabase(appCompatActivity).appDao();
        // prepare the request queue
        requestQueue = Volley.newRequestQueue(appCompatActivity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart() {
        Log.d(TAG, "start polling ...");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override public void run() {
                poll();
            }
        }, DELAY, PERIOD);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop() {
        Log.d(TAG, "... stop polling");
        timer.cancel();
    }

    private void poll() {
        requestQueue.add(pollIssNowRequest);
    }

    private void handleResponse(final String responseText) {
        final GithubFollower [] githubFollowers = gson.fromJson(responseText, GithubFollower[].class);
        appDao.insert(githubFollowers);
//        Log.d(TAG, "followersResponse: " + followers);
    }

    private void handleError(final VolleyError volleyError) {
        Log.e(TAG, "Network error: " + volleyError.getMessage());
    }
}