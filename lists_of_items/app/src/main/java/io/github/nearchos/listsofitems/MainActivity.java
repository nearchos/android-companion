package io.github.nearchos.listsofitems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static final int SIZE_OF_DATA = 100;
    private Vector<Double> data = new Vector<>();

    private RecyclerView recyclerView;
    private NumberAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        // initiate data
        for(int i = 0; i < SIZE_OF_DATA; i++) {
            this.data.add(i+1.0);
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // specify an adapter (see also next example)
        adapter = new NumberAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Modify existing data by adding 0.1 to each value
     * @param view
     */
    public void modify(final View view) {
        Log.d("android-companion", "modify");
        for(int i = 0; i < this.data.size(); i++) {
            this.data.set(i, this.data.get(i) + 0.1);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Add another 10 values at the end of the existing data
     * @param view
     */
    public void append(final View view) {
        Log.d("android-companion", "append");
        // 'remember' the first visible item position
        final double lastValue = this.data.lastElement();
        for(int i = 0; i < 10; i++) {
            this.data.add(lastValue + i + 1);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Add another 10 values at the start of the existing data
     * @param view
     */
    public void prepend(final View view) {
        Log.d("android-companion", "prepend");
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        final double firstValue = this.data.firstElement();
        for(int i = 0; i < 10; i++) {
            this.data.add(0, firstValue - i - 1);
        }
        adapter.notifyDataSetChanged();
        // scroll to ensure that the same item is still the first one visible
        linearLayoutManager.scrollToPosition(lastVisibleItemPosition+10);
        Log.d("android-companion", "lastVisibleItemPosition:" + lastVisibleItemPosition);
    }
}