package io.github.nearchos.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    public static final String SERVICE_URL =
            "https://jsonplaceholder.typicode.com/todos";

    private TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textViewUrl = findViewById(R.id.textViewUrl);
        textViewUrl.setText(SERVICE_URL);

        this.textViewResponse = findViewById(R.id.textViewResponse);
    }

    public void go(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                SERVICE_URL,
                response -> textViewResponse.setText(response),
                volleyError -> textViewResponse.setText(volleyError.getMessage()));

        requestQueue.add(request);
    }

    public void openTodoList(View view) {
        startActivity(new Intent(this, TasksActivity.class));
    }

    public void openPhotoList(View view) {
        startActivity(new Intent(this, PhotosActivity.class));
    }
}