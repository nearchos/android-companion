package io.github.nearchos.lifecycleandintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class IntentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int counter = getIntent().getIntExtra("extra-counter", 0);
        Log.d(MainActivity.TAG, "counter: " + counter);
    }

    public void startWebIntent(View view) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse("https://github.com/nearchos/android-companion"));
        if(webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        } else {
            Log.e(MainActivity.TAG, "There is no activity able to handle the intent: " + webIntent.getAction());
        }
    }
}