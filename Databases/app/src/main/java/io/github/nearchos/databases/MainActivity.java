package io.github.nearchos.databases;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        DatabaseOpenHelper doh = new DatabaseOpenHelper(this); // get helper
        SQLiteDatabase db = doh.getReadableDatabase(); // read-only database instance
        Cursor cursor = db.rawQuery("SELECT * FROM entries", null); // query
        int numOfRows = cursor.getCount(); // number of rows in the result set
        final String [] titles = new String[numOfRows]; // titles
        final String [] bodies = new String[numOfRows]; // bodies - ignored
        cursor.moveToFirst();
        int columnTitleIndex = cursor.getColumnIndex("title");
        int columnBodyIndex = cursor.getColumnIndex("body");
        for(int i = 0; i < numOfRows; i++) {
            titles[i] = cursor.getString(columnTitleIndex);
            bodies[i] = cursor.getString(columnBodyIndex);
            cursor.moveToNext();
        }
        cursor.close();


        ArrayAdapter adapter = new ArrayAdapter<>( // update the data ...
                this, android.R.layout.simple_expandable_list_item_1, titles);
        listView.setAdapter(adapter); // ... and notify the adapter to update
    }

    public void addNewEntry(View view) {
        final Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}