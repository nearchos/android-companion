package io.github.nearchos.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "android-companion";

    private TextView textView;
    private ListView listView;
    private Button button;

    // Instantiate the RequestQueue.
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        this.textView = findViewById(R.id.textView);
        this.listView = findViewById(R.id.listView);
        this.button = findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWithGson();
            }
        });
    }

    // see https://jsonplaceholder.typicode.com
    public static final String SERVICE_URL = "https://jsonplaceholder.typicode.com/todos";

    private void fetch() {

        final JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                SERVICE_URL,
                null,
                jsonArrayListener,
                errorListener);

        requestQueue.add(request);
    }

    private void fetchWithGson() {

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                SERVICE_URL,
                stringListener,
                errorListener);

        requestQueue.add(request);
    }

    private void updateList(final Vector<Todo> todos) {
        textView.setText(new Date().toString());
        final ListAdapter listAdapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, todos);
        listView.setAdapter(listAdapter);
    }

    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            final Vector<Todo> todos = new Vector<>();
            for(int i = 0; i < response.length(); i++) {
                try {
                    final JSONObject jsonObject = response.getJSONObject(i);
                    Todo todo = new Todo(jsonObject.getInt("userId"),
                            jsonObject.getInt("id"),
                            jsonObject.getString("title"),
                            jsonObject.getBoolean("completed"));
                    todos.add(todo);
                } catch (JSONException jsone) {
                    // todo handle JSON error
                }
                updateList(todos);
            }
        }
    };

    private Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            final Todo [] todosArray = gson.fromJson(response, Todo[].class);
            final Vector<Todo> todos = new Vector<>(Arrays.asList(todosArray));
            updateList(todos);
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, error.getMessage()); // todo handle error
        }
    };
}