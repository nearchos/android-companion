package io.github.nearchos.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextTitle = findViewById(R.id.title_edit_text);
        editTextBody = findViewById(R.id.body_edit_text);
    }

    public void discard(View view) { // do not save data -- i.e. discard it
        finish(); // close this activity (goes back to parent)
    }

    public void save(View view) { // save data using Room persistence library
        final String title = editTextTitle.getText().toString();
        final String body = editTextBody.getText().toString();
        Log.d("room", "Add: " + title + ", " + body);
        Entry entry = new Entry(title, body);
        // store to Room
        BlogRoomDatabase
                .getDatabase(this)
                .blogDao()
                .insert(entry);
        finish(); // close this activity (goes back to parent)
    }
}