package io.github.nearchos.archcomp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import io.github.nearchos.archcomp.github_api.GithubFollowersActivity;
import io.github.nearchos.archcomp.power.PowerActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "mad-book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPowerActivity(final View view) {
        startActivity(new Intent(this, PowerActivity.class));
    }

//    public void startIssPositionActivity(final View view) {
//        startActivity(new Intent(this, IssPositionActivity.class));
//    }
//
//    public void startIssLatestPositionsActivity(final View view) {
//        startActivity(new Intent(this, IssLatestPositionsActivity.class));
//    }

    public void startGithubFollowersActivity(final View view) {
        startActivity(new Intent(this, GithubFollowersActivity.class));
    }
}