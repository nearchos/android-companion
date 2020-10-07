package io.github.nearchos.fingerchase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FingerChaseView fingerChaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fingerChaseView = new FingerChaseView(this);
        setContentView(fingerChaseView);
    }
}