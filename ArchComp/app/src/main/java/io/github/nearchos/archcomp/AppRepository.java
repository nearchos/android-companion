/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.archcomp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.github.nearchos.archcomp.MainActivity.TAG;

class AppRepository {

    private AppDao appDao;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private Executor executor = Executors.newSingleThreadExecutor();
    private RequestQueue queue;

    public AppRepository(final Application application) {
        final AppDatabase appDatabase = AppDatabase.getDatabase(application);
        this.appDao = appDatabase.appDao();
        this.sharedPreferences = application.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        this.queue = Volley.newRequestQueue(application);

        // if needed, make a request to online data source -- assume this is not needed if less than 10 min since last request
        final long lastRequestTimestamp = sharedPreferences.getLong(PREF_KEY_LAST_REQUEST_TIMESTAMP, 0L);
        final boolean onlineRequestNeeded = System.currentTimeMillis() - lastRequestTimestamp > TEN_MINUTES;
        if(onlineRequestNeeded) {
            refresh();
        }
    }

    public static final long TEN_MINUTES = 10L * 60 * 1000; // 10 min * 60 sec * 1000 ms
    public static final String PREF_KEY_LAST_REQUEST_TIMESTAMP = "lastRequestTimestamp";

    public static final String GITHUB_URL = "https://api.github.com/users/octocat/followers";

    void refresh() { // force a query to remote service to fetch data
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                GITHUB_URL,
                this::handleResponse,
                this::handleError
        );
        stringRequest.setShouldCache(false); // this is needed to ensure no cached data is returned when disconnected
        this.queue.add(stringRequest);
    }

    private void handleResponse(final String response) {
        // convert text reply to class
        final GithubFollower [] githubFollowers = gson.fromJson(response, GithubFollower[].class);
        // insert data in Room -- run on separate thread to avoid blocking UI thread
        Log.d(TAG, "githubFollowers: " + githubFollowers.length);
        if(githubFollowers.length > 0) {
            executor.execute(() -> appDao.insert(githubFollowers));
            // update prefs with update timestamp
            sharedPreferences.edit().putLong(PREF_KEY_LAST_REQUEST_TIMESTAMP, System.currentTimeMillis()).apply();
        }
    }

    private void handleError(final VolleyError error) {
        Log.e(TAG, "Error while connecting to network: " + error);
    }
}
