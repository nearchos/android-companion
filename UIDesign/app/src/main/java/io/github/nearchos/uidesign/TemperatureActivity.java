package io.github.nearchos.uidesign;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TemperatureActivity extends AppCompatActivity {

    private EditText celsiusEditText;
    private EditText fahrenheitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        this.celsiusEditText = findViewById(R.id.celsiusEditText);
        this.fahrenheitEditText = findViewById(R.id.fahrenheitEditText);

        this.celsiusEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { /* nothing */ }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { /* nothing */ }

            @Override
            public void afterTextChanged(Editable editable) {
                double celsius = Double.parseDouble(editable.toString());
                double fahrenheit = celsius * 9d / 5 + 32;
                fahrenheitEditText.setText(Double.toString(fahrenheit));
            }
        });

        this.celsiusEditText.setText("20.0");
    }
}
