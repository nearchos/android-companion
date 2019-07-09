package io.github.nearchos.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "android-companion";

    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = findViewById(R.id.textView);
        this.listView = findViewById(R.id.listView);
    }

    private void updateList(final List<Todo> todos) {
        textView.setText(new Date().toString());
        final ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todos);
        listView.setAdapter(listAdapter);
    }

    public static final String SERVICE_URL =
            "https://jsonplaceholder.typicode.com/todos";

    public void fetch(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

//        final JsonArrayRequest request = new JsonArrayRequest(
//                Request.Method.GET,
//                SERVICE_URL,
//                null, // an optional parameter to be passed with the request
//                jsonArrayListener,
//                errorListener);

        final Request request = new StringRequest(
                Request.Method.GET,
                SERVICE_URL,
                stringListener,
                errorListener);

        requestQueue.add(request);
    }

//    private void fetchWithGson() {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        Request
//        final StringRequest request = new StringRequest(
//                Request.Method.GET,
//                SERVICE_URL,
//                stringListener,
//                errorListener);
//
//        requestQueue.add(request);
//    }

    private Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // use Gson to convert response to array of items
            final Todo [] todos = new Gson().fromJson(response, Todo[].class);
            // Arrays.asList(...) is used to convert an array to list
            List<Todo> todosList = Arrays.asList(todos);
            updateList(todosList);
        }
    };

//    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
//        @Override
//        public void onResponse(JSONArray response) {
//            final Vector<Todo> todos = new Vector<>();
//            for(int i = 0; i < response.length(); i++) {
//                try {
//                    final JSONObject jsonObject = response.getJSONObject(i);
//                    final Todo todo = new Todo(jsonObject);
//                    todos.add(todo);
//                } catch (JSONException jsone) {
//                    throw new RuntimeException(jsone); // escalate error
//                }
//            }
//            updateList(todos);
//        }
//    };

//    private Response.Listener<String> stringListener = new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            Gson gson = new Gson();
//            final Todo [] todosArray = gson.fromJson(response, Todo[].class);
//            final Vector<Todo> todos = new Vector<>(Arrays.asList(todosArray));
//            updateList(todos);
//        }
//    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Volley error: " + error.getMessage());
        }
    };
}