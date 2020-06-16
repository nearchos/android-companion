package io.github.nearchos.archcomp.github_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import io.github.nearchos.archcomp.R;

public class GithubFollowersActivity extends AppCompatActivity {

    public static final String DEFAULT_ROOT = "octocat";

    private TextView textViewGithubFollowersRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_followers);

        this.textViewGithubFollowersRoot = findViewById(R.id.textViewGithubFollowersRoot);
        final RecyclerView recyclerViewGithubFollowers = findViewById(R.id.listViewGithubFollowers);
        final GithubFollowerListAdapter githubFollowerListAdapter = new GithubFollowerListAdapter(this);
        recyclerViewGithubFollowers.setAdapter(githubFollowerListAdapter);
        recyclerViewGithubFollowers.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRoot(DEFAULT_ROOT);
    }

    private void setRoot(final String name) {
        textViewGithubFollowersRoot.setText(name);
    }
}