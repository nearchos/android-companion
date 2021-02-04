package io.github.nearchos.justchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editText = findViewById(R.id.editText);
        findViewById(R.id.buttonConnect).setOnClickListener(v -> connect());
    }

    private void connect() {
        final String userAlias = editText.getText().toString();
        if(userAlias.trim().isEmpty()) {
            Snackbar.make(editText, R.string.The_alias_cannot_be_empty, BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            final Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("alias", userAlias);
            startActivity(intent);
        }
    }
}