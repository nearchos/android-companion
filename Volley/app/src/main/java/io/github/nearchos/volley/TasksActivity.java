package io.github.nearchos.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TasksActivity extends AppCompatActivity {

    public static final String TAG = "mad-book";

    private final List<Task> tasks = new Vector<>();

    private ListView listView;
    private ArrayAdapter<Task> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        this.listView = findViewById(R.id.listView);
        this.arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                MainActivity.SERVICE_URL,
                this::handleResponse, // method reference, equivalent to: response -> handleResponse(response)
                this::handleError); // method reference, equivalent to: error -> handleError(error)

        requestQueue.add(request);
    }

    private void handleResponse(final String response) {
        // use Gson to convert response to array of items
        final Task[] taskArray = new Gson().fromJson(response, Task[].class);
        Snackbar.make(listView, "Read " + taskArray.length + " items!", BaseTransientBottomBar.LENGTH_SHORT).show();
        // Arrays.asList(...) is used to convert an array to list
        this.tasks.clear();
        this.tasks.addAll(Arrays.asList(taskArray));
        arrayAdapter.notifyDataSetChanged();
    }

    private void handleError(VolleyError volleyError) {
        Log.e(TAG, volleyError.getMessage());
        Snackbar.make(listView, "Volley error", BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}