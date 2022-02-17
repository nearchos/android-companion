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

/**
 * Simply used to list the available Albums, and allow the user to select one.
 * Once an album is selected, the PhotosActivity is launched, with the selected album as a
 * parameter.
 */
public class AlbumsActivity extends AppCompatActivity {

    // contains the albums to be listed - initially empty
    private final Vector<Album> albums = new Vector<>();

    // Volley queue used to make HTTP requests
    private RequestQueue requestQueue;

    // The DAO used to access the Room database
    private AppDao appDao;

    private ArrayAdapter<Album> albumsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        // set the title and add the 'back' button in the action bar
        setTitle("Select album");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init the Volley request queue
        this.requestQueue = Volley.newRequestQueue(this);
        // init the DAO
        this.appDao = AppDatabase.getDatabase(this).appDao();

        final ListView albumsListView = findViewById(R.id.albumsListView);
        albumsListView.setOnItemClickListener((adapterView, view, i, l) -> selectAlbum(albums.get(i)));

        albumsArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, albums);
        albumsListView.setAdapter(albumsArrayAdapter);
    }

    /**
     * This is required to realize the 'back' button functionality in the action bar.
     * @return true as the action completes successfully
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetch(); // initiates a 'fetch' when the activity is displayed
    }

    /**#
     * Handles the event of selecting an Album. The response it to start the PhotosActivity,
     * passing it the album ID and title.
     * @param album the album to be displayed
     */
    private void selectAlbum(final Album album) {
        Intent intent = new Intent(this, PhotosActivity.class);
        intent.putExtra("albumId", album.getId());
        intent.putExtra("albumTitle", album.getTitle());
        startActivity(intent);
    }

    /**
     * Implements a HTTP GET request to fetch the albums from the JSONPlaceholder url.
     */
    private void fetch() {

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums", // edit the URL
                this::handleResponse,
                this::handleError);

        requestQueue.add(request);
    }

    /**
     * Handles the positive outcome of the HTTP request.
     *
     * @param response the JSON-formatted string containing the response
     */
    private void handleResponse(final String response) {
        final Album [] fetchedAlbums = new Gson().fromJson(response, Album[].class);

        Executors.newSingleThreadExecutor().execute(() -> {
            // update the database
            appDao.insert(fetchedAlbums);
            // shows the data from the database
            refresh();
        });
    }

    /**
     * Handles the error case of the HTTP request. Simply shows a warning toast message.
     * @param volleyError describes the underlying problem
     */
    private void handleError(final VolleyError volleyError) {
        Toast.makeText(this, "Network error. Are you offline?", Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the displayed items using the latest values from the database.
     */
    private void refresh() {
        Executors.newSingleThreadExecutor().execute(() -> {
            final List<Album> updatedAlbums = appDao.getAlbums();
            albums.clear();
            albums.addAll(updatedAlbums);
            runOnUiThread(() -> albumsArrayAdapter.notifyDataSetChanged());
        });
    }
}