package io.github.nearchos.persistence;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private EditText editText;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.check_box);
        editText = findViewById(R.id.edit_text);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // current values to be persisted
        boolean isChecked = checkBox.isChecked();
        String tweet = editText.getText().toString();
        // storing in shared preferences...
        sharedPreferences
                .edit()
                .putBoolean("checked", isChecked)
                .putString("tweet", tweet)
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // getting stored value from preferences
        boolean isChecked = sharedPreferences.getBoolean("checked", false);
        String tweet = sharedPreferences.getString("tweet", "");
        // setting the values of the UI elements
        checkBox.setChecked(isChecked);
        editText.setText(tweet);
    }
}
