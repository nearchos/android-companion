package io.github.nearchos.databases;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText bodyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        titleEditText = findViewById(R.id.title_edit_text);
        bodyEditText = findViewById(R.id.body_edit_text);
    }

    public void discard(View view) { // do not save data -- i.e. discard it
        finish(); // stop this activity and return to parent
    }

    public void save(View view) {
        final String title = titleEditText.getText().toString();
        final String body = bodyEditText.getText().toString();

        if(title.isEmpty()) {
            Toast.makeText(this, "The 'title' must be non-empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            // store in DB
            DatabaseOpenHelper doh = new DatabaseOpenHelper(this);
            SQLiteDatabase db = doh.getWritableDatabase(); // note we get writable DB
            ContentValues contentValues = new ContentValues(); // a map data structure
            contentValues.put("title", title);
            contentValues.put("body", body);
            contentValues.put("timestamp", System.currentTimeMillis());
            db.insert("entries", null, contentValues);

            finish(); // stop this activity and return to parent
        }
    }
}