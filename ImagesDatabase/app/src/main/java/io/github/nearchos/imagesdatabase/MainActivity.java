package io.github.nearchos.imagesdatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1)
                .setOnClickListener(v -> // lambda expressions require Java 8
                        startActivity(new Intent(this, UriImagesActivity.class)));

        findViewById(R.id.button2)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, RawImagesActivity.class)));
    }
}