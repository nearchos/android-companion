package io.github.nearchos.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRelative(View view) {
        startActivity(new Intent(this, RelativeActivity.class));
    }

    public void startTemperature(View view) {
        startActivity(new Intent(this, TemperatureActivity.class));
    }
}
