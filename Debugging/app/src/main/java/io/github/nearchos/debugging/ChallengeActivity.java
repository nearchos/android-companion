package io.github.nearchos.debugging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

/**
 * This activity is a calculator realizing simple arithmetic calculations (addition, subtraction,
 * multiplication and division). It purposely includes some deficiencies and bugs. Your goal is to
 * find and fix them. Specifically, you are asked to:
 * 1. identify any layout issues in the corresponding R.layout.activity_challenge layout.
 * 2. find and fix the bug that causes it to not produce the correct result in all scenarios.
 * 3. find and fix the bug that causes it to crash when you provide certain input.
 */
public class ChallengeActivity extends AppCompatActivity {

    private EditText editText1;
    private Spinner spinner;
    private EditText editText2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        this.editText1 = findViewById(R.id.editText1);
        this.spinner = findViewById(R.id.spinner);
        this.editText2 = findViewById(R.id.editText2);
        this.textView = findViewById(R.id.textView);

        final ArrayAdapter<Operation> operationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Operation.values());
        spinner.setAdapter(operationArrayAdapter);
    }

    public void compute(final View view) {
        String text1 = editText1.getText().toString();
        double number1 = Double.parseDouble(text1);
        String text2 = editText2.getText().toString();
        double number2 = Double.parseDouble(text2);
        Operation operation = (Operation) spinner.getSelectedItem();
        double result = 0d;
        switch (operation) {
            case ADD:
                result = number1 + number2;
            case SUBTRACT:
                result = number1 - number2;
            case MULTIPLY:
                result = number1 * number2;
            case DIVIDE:
                result = number1 / number2;
        }
        textView.setText(String.format(Locale.US, "%.2f", result));
    }

    enum Operation {

        ADD('+'),
        SUBTRACT('-'),
        MULTIPLY('*'),
        DIVIDE('/')
        ;

        private char c;

        Operation(final char c) {
            this.c = c;
        }

        public char getC() {
            return c;
        }

        @NonNull
        @Override
        public String toString() {
            return Character.toString(c);
        }
    }
}