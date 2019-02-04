package io.github.nearchos.lifecycleandintents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "android-companion-tag";

    public void startActivity(View view) {
        Intent intent = new Intent(this, IntentsActivity.class);
        int counter = 7;
        intent.putExtra("extra-counter", counter);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "hello from 'onCreate()'");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "hello from 'onStart()'");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "hello from 'onResume()'");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "hello from 'onPause()'");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "hello from 'onStop()'");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "hello from 'onDestroy()'");
    }
}