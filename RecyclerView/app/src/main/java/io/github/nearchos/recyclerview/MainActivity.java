package io.github.nearchos.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.Vector;

public class MainActivity
        extends AppCompatActivity
        implements NumberAdapter.OnItemClickListener, NumberAdapter.OnItemLongClickListener {

    public static final int SIZE_OF_DATA = 10;
    public static final int SIZE_OF_INCREMENT = 4;
    private final Vector<Double> data = new Vector<>();

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
        // add divider decoration between items
        recyclerView.addItemDecoration(new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        // specify an adapter (see also next example)
        adapter = new NumberAdapter(data, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void itemClicked(int pos, double value) {
        Snackbar.make(recyclerView, String.format(Locale.ENGLISH, "%.1f (long press to delete)", data.get(pos)), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean itemLongClicked(int pos, double value) {
        data.remove(pos);
        Snackbar.make(recyclerView, "Deleted " + String.format(Locale.ENGLISH, "%.1f", value), 5000) // show for 5 seconds
                .setAction("Undo", view -> undo(pos, value))
                .show();
        adapter.notifyDataSetChanged();
        return true;
    }

    private void undo(int pos, double value) {
        data.add(pos, value);
        adapter.notifyDataSetChanged();
    }

    /**
     * Modify existing data by adding 0.1 to each value
     */
    public void modify(final View view) {
        Log.d("android-companion", "modify");
        for(int i = 0; i < this.data.size(); i++) {
            this.data.set(i, this.data.get(i) + 0.1);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Add values at the end of the existing data
     */
    public void append(final View view) {
        Log.d("android-companion", "append");
        final double lastValue = this.data.lastElement();
        for(int i = 0; i < SIZE_OF_INCREMENT; i++) {
            this.data.add(lastValue + i + 1);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Add values at the start of the existing data
     */
    public void prepend(final View view) {
        Log.d("android-companion", "prepend");
        final int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        final double firstValue = this.data.firstElement();
        for(int i = 0; i < SIZE_OF_INCREMENT; i++) {
            this.data.add(0, firstValue - i - 1);
        }
        adapter.notifyDataSetChanged();
        // scroll so that the same item is still the first one visible
        linearLayoutManager.scrollToPosition(lastVisibleItemPosition + SIZE_OF_INCREMENT - 1);
        Log.d("android-companion", "lastVisibleItemPosition:" + lastVisibleItemPosition);
    }
}