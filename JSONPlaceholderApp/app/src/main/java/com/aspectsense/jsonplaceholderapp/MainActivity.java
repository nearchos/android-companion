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

public class MainActivity extends AppCompatActivity {

    private final Vector<Album> albums = new Vector<>();

    private RequestQueue requestQueue;
    private AppDao appDao;

    private ListView albumsListView;
    private ArrayAdapter<Album> albumsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.requestQueue = Volley.newRequestQueue(this);
        this.appDao = AppDatabase.getDatabase(this).appDao();

        albumsListView = findViewById(R.id.albumsListView);
        albumsListView.setOnItemClickListener((adapterView, view, i, l) -> selectAlbum(albums.get(i)));

        albumsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, albums);
        albumsListView.setAdapter(albumsArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetch();
//        Executors.newSingleThreadExecutor().execute(() -> {
//            for(int i = 0; i < 20; i++) {
//                appDao.insert(new Album(i, i, "Album-" + i));
//            }
//        });
//
//        Executors.newSingleThreadExecutor().execute(() -> {
//            albums.clear();
//            albums.addAll(appDao.getAlbums());
//            runOnUiThread(() -> albumsArrayAdapter.notifyDataSetChanged());
//        });
    }

    private void selectAlbum(final Album album) {
        Intent intent = new Intent(this, PhotosActivity.class);
        intent.putExtra("albumId", album.getId());
        intent.putExtra("albumTitle", album.getTitle());
        startActivity(intent);
    }

    private void fetch() {

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums", // edit the URL
                this::handleResponse,
                this::handleError);

        requestQueue.add(request);
    }

    private void handleResponse(final String response) {
        final Album [] fetchedAlbums = new Gson().fromJson(response, Album[].class);

        Executors.newSingleThreadExecutor().execute(() -> {
            appDao.insert(fetchedAlbums);
            refresh();
        });
    }

    private void handleError(final VolleyError volleyError) {
        Toast.makeText(this, "Network error: " + volleyError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private void refresh() {
        Executors.newSingleThreadExecutor().execute(() -> {
            final List<Album> updatedAlbums = appDao.getAlbums();
            albums.clear();
            albums.addAll(updatedAlbums);
            runOnUiThread(() -> albumsArrayAdapter.notifyDataSetChanged());
        });
    }
}