package io.github.nearchos.smarthomeswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ChallengeActivity extends AppCompatActivity {

    private TextView topStart;
    private TextView topEnd;
    private TextView bottomStart;
    private TextView bottomEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        topStart = findViewById(R.id.topStartTextView);
        topEnd = findViewById(R.id.topEndTextView);
        bottomStart = findViewById(R.id.bottomStartTextView);
        bottomEnd = findViewById(R.id.bottomEndTextView);

        final ToggleButton topStartToggleButton = findViewById(R.id.topStartToggleButton);
        topStartToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleTopStart(topStart, isChecked));
        final ToggleButton topEndToggleButton = findViewById(R.id.topEndToggleButton);
        topEndToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleTopStart(topEnd, isChecked));
        final ToggleButton bottomStartToggleButton = findViewById(R.id.bottomStartToggleButton);
        bottomStartToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleTopStart(bottomStart, isChecked));
        final ToggleButton bottomEndToggleButton = findViewById(R.id.bottomEndToggleButton);
        bottomEndToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> toggleTopStart(bottomEnd, isChecked));
    }

    private void toggleTopStart(final TextView textView, final boolean onOff) {
        textView.setBackgroundColor(onOff ? Color.WHITE : Color.BLACK);
        textView.setTextColor(onOff ? Color.BLACK : Color.WHITE);
    }
}