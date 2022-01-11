package io.github.nearchos.room;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

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

        final List<Entry> allEntries = BlogRoomDatabase
                .getDatabase(this)
                .blogDao()
                .getAllEntries();
        final ListAdapter entriesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allEntries);
        listView.setAdapter(entriesAdapter);
    }

    public void addNewEntry(View view) {
        final Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}