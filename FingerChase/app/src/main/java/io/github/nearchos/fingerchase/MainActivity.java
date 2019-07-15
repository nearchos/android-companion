package io.github.nearchos.fingerchase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FingerChaseView fingerChaseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fingerChaseView = new FingerChaseView(this);
        setContentView(fingerChaseView);
    }
}