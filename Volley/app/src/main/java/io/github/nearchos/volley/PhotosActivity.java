package io.github.nearchos.volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Vector;

public class PhotosActivity extends AppCompatActivity {

    private SingletonRequestQueue singletonRequestQueue;

    private final Vector<Photo> photos = new Vector<>();

    private final StringRequest stringRequest = new StringRequest(
            Request.Method.GET,
            "https://jsonplaceholder.typicode.com/photos",
            this::handleResponse,
            this::handleError
    );

    private CoordinatorLayout coordinatorLayout;
    private PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        photosAdapter = new PhotosAdapter(this, photos);
        recyclerView.setAdapter(photosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        this.singletonRequestQueue = SingletonRequestQueue.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.singletonRequestQueue.getRequestQueue().add(stringRequest);
    }

    private void handleResponse(final String response) {
        // use Gson to convert response to array of photo items
        final Photo [] fetchedPhotos = new Gson().fromJson(response, Photo[].class);
        Snackbar.make(coordinatorLayout, "Fetched photos: " + fetchedPhotos.length, BaseTransientBottomBar.LENGTH_SHORT).show();
        // Arrays.asList(...) is used to convert an array to list
        photos.clear(); // first make sure the list is empty
        photos.addAll(Arrays.asList(fetchedPhotos)); // then add all fetched photos
        photosAdapter.notifyDataSetChanged(); // make sure the recycler view is updated
    }

    private void handleError(final VolleyError volleyError) {
        Snackbar.make(coordinatorLayout, "Error while fetching images: " + volleyError.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}