package io.github.nearchos.debugging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "mad-book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void trace(final View view) {
        int a = 0;
        a++;
        Log.d(TAG, "a: " + a);
        int [] b = { 1, 2, 3 };
        Log.d(TAG, "b: " + Arrays.toString(b));
    }

    public void crash(final View view) {
        Log.d(TAG, "Before a runtime exception");
        int a = 1 / 0;
    }

    public void challenge(final View view) {
        startActivity(new Intent(this, ChallengeActivity.class));
    }
}