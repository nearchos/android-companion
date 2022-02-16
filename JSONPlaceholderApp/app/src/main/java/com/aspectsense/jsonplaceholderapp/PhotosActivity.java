package com.aspectsense.jsonplaceholderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;

public class PhotosActivity extends AppCompatActivity {

    private final Vector<Photo> photos = new Vector<>();

    private RequestQueue requestQueue;
    private AppDao appDao;

    private ListView photosListView;
    private ArrayAdapter<Photo> photosArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        this.requestQueue = Volley.newRequestQueue(this);
        this.appDao = AppDatabase.getDatabase(this).appDao();

        photosListView = findViewById(R.id.photosListView);

        photosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, photos);
        photosListView.setAdapter(photosArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        final int albumId = intent.getIntExtra("albumId", 0);
        final String albumTitle = intent.getStringExtra("albumTitle");
        setTitle(albumTitle);

        fetch(albumId);
//        Executors.newSingleThreadExecutor().execute(() -> {
//            for(int i = 0; i < 20; i++) {
//                appDao.insert(new Photo(i, i, "Photo-" + i, "htts...", "htts..."));
//            }
//        });
//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            photos.clear();
//            photos.addAll(appDao.getPhotos(albumId));
//            runOnUiThread(() -> photosArrayAdapter.notifyDataSetChanged());
//        });
    }

    private void fetch(final int albumId) {

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums/" + albumId + "/photos", // edit the URL
                response -> handleResponse(response, albumId),
                this::handleError);

        requestQueue.add(request);
    }

    private void handleResponse(final String response, final int albumId) {
        final Photo [] fetchedPhotos = new Gson().fromJson(response, Photo[].class);

        Executors.newSingleThreadExecutor().execute(() -> {
            appDao.insert(fetchedPhotos);
            refresh(albumId);
        });
    }

    private void handleError(final VolleyError volleyError) {
        Toast.makeText(this, "Network error: " + volleyError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void refresh(final int albumId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            final List<Photo> updatedPhotos = appDao.getPhotos(albumId);
            photos.clear();
            photos.addAll(updatedPhotos);
            runOnUiThread(() -> photosArrayAdapter.notifyDataSetChanged());
        });
    }
}