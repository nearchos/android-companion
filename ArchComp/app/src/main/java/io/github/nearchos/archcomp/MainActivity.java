/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.archcomp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static io.github.nearchos.archcomp.AppRepository.PREF_KEY_LAST_REQUEST_TIMESTAMP;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "mad-book";

    private ImageView imageViewConnected;
    private TextView textViewConnected;
    private RecyclerView recyclerViewGithubFollowers;
    private TextView textViewUpdated;
    private Button buttonRefresh;

    private static final SimpleDateFormat DEFAULT_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private ConnectivityManager connectivityManager;
    private NetworkStatusViewModel networkStatusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageViewConnected = findViewById(R.id.imageViewConnected);
        this.textViewConnected = findViewById(R.id.textViewConnected);
        this.recyclerViewGithubFollowers = findViewById(R.id.recyclerViewGithubFollowers);
        this.textViewUpdated = findViewById(R.id.textViewUpdated);
        this.buttonRefresh = findViewById(R.id.buttonRefresh);

        this.connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        this.networkStatusViewModel = new ViewModelProvider(this).get(NetworkStatusViewModel.class);

        this.networkStatusViewModel.getConnected().observe(this, isConnected -> {
            this.imageViewConnected.setImageResource(isConnected ? R.drawable.ic_wifi : R.drawable.ic_wifi_off);
            this.textViewConnected.setText(isConnected ? R.string.Connected : R.string.No_connection);
        });

        final GithubFollowerListAdapter githubFollowerListAdapter = new GithubFollowerListAdapter(this);
        githubFollowerListAdapter.setOnGithubFollowerSelected((position, githubFollower) -> showGithubFollower(githubFollower));
        recyclerViewGithubFollowers.setAdapter(githubFollowerListAdapter);
        recyclerViewGithubFollowers.setLayoutManager(new LinearLayoutManager(this));

        final SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        final GithubFollowersViewModel githubFollowersViewModel = new ViewModelProvider(this).get(GithubFollowersViewModel.class);
        githubFollowersViewModel.getLiveData().observe(this, (githubFollowers) -> {
            githubFollowerListAdapter.setGithubFollowers(githubFollowers);
            final long lastRequestTimestamp = sharedPreferences.getLong(PREF_KEY_LAST_REQUEST_TIMESTAMP, 0L);
            final String lastUpdated = DEFAULT_SIMPLE_DATE_FORMAT.format(new Date(lastRequestTimestamp));
            this.textViewUpdated.setText(getString(R.string.Updated, lastUpdated));
        });

        final AppRepository appRepository = new AppRepository(getApplication());
        this.buttonRefresh.setOnClickListener(v -> appRepository.refresh());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // registerNetworkCallback
        if(connectivityManager == null) return; // skip if null
        final NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback); // requires permission ACCESS_NETWORK_STATE
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregisterNetworkCallback
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            networkStatusViewModel.setConnected(true);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            networkStatusViewModel.setConnected(false);
        }
    };

    private void showGithubFollower(final GithubFollower githubFollower) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ImageView imageView = new ImageView(this);
        Picasso.get() // Picasso is a popular library for loading and caching images in Android (https://github.com/square/picasso)
                .load(githubFollower.getAvatarUrl())
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(R.drawable.ic_baseline_sync_problem_24)
                .into(imageView);

        builder.setTitle(githubFollower.getLogin())
                .setView(imageView)
                .setPositiveButton(R.string.Dismiss, null)
                .show();
    }
}