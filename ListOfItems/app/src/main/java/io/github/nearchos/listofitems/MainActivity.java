package io.github.nearchos.listofitems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String [] dummyData = {
            "Hello", "World", "This", "Is", "An", "Array", "Of", "Dummy", "Data" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myListView = findViewById(R.id.myListView);
        ListAdapter myListAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dummyData);
        myListView.setAdapter(myListAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = dummyData[position];
                Toast.makeText(MainActivity.this, selected, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, CurrenciesActivity.class));
            }
        });
    }
}