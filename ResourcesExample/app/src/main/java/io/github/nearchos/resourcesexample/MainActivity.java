package io.github.nearchos.resourcesexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = findViewById(R.id.activity_main_textView);
        this.textView.setTextSize(20);

        textView = findViewById(R.id.activity_main_textView);
        textView.setVisibility(View.INVISIBLE);

        buttonB = findViewById(R.id.activity_main_buttonB);

        MyListener myListener = new MyListener();
        buttonB.setOnClickListener(myListener);

        buttonB.setOnClickListener(new MyListener());

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textView.getVisibility() == View.INVISIBLE) {
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonB.setOnClickListener(view -> {
            if(textView.getVisibility() == View.INVISIBLE) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void handleButtonA(View view) {
        if(textView.getVisibility() == View.INVISIBLE) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            // handle click
        }
    }
}