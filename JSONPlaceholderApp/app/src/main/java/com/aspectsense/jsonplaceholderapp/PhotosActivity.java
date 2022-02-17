package com.aspectsense.jsonplaceholderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
 * Used to list the available Photos in a selected Album, and allow the user to mark them as
 * favorite, or not favorite.
 */
public class PhotosActivity extends AppCompatActivity implements PhotosRecyclerAdapter.OnFavoriteChangedListener {

    // contains the photos to be listed - initially empty
    private final Vector<Photo> photos = new Vector<>();

    // Volley queue used to make HTTP requests
    private RequestQueue requestQueue;

    // The DAO used to access the Room database
    private AppDao appDao;

    private TextView albumTextView;

    private PhotosRecyclerAdapter photosRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // set the title and add the 'back' button in the action bar
        setTitle("Select favorite photos");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init the Volley request queue
        this.requestQueue = Volley.newRequestQueue(this);
        // init the DAO
        this.appDao = AppDatabase.getDatabase(this).appDao();

        albumTextView = findViewById(R.id.albumTextView);

        final RecyclerView photosRecyclerView = findViewById(R.id.photosRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        photosRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(photosRecyclerView.getContext(), linearLayoutManager.getOrientation());
        photosRecyclerView.addItemDecoration(dividerItemDecoration);

        photosRecyclerAdapter = new PhotosRecyclerAdapter(getApplicationContext(), photos, this);
        photosRecyclerView.setAdapter(photosRecyclerAdapter);
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

        Intent intent = getIntent();
        final int albumId = intent.getIntExtra("albumId", 0);
        final String albumTitle = intent.getStringExtra("albumTitle");
        albumTextView.setText(albumTitle);

        fetch(albumId); // initiates a 'fetch' for the given album ID when the activity is displayed
    }

    /**
     * Implements a HTTP GET request to fetch the photos for the given album from the JSONPlaceholder url.
     */
    private void fetch(final int albumId) {

        final StringRequest request = new StringRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums/" + albumId + "/photos", // edit the URL
                response -> handleResponse(response, albumId),
                this::handleError);

        requestQueue.add(request);
    }

    /**
     * Handles the positive outcome of the HTTP request.
     *
     * @param response the JSON-formatted string containing the response
     */
    private void handleResponse(final String response, final int albumId) {
        final Photo [] fetchedPhotos = new Gson().fromJson(response, Photo[].class);

        Executors.newSingleThreadExecutor().execute(() -> {
            Log.d("JSONPlaceholder", "1. appDao.getFavoritePhotos() -> " + appDao.getFavoritePhotos());//todo
            appDao.insert(fetchedPhotos);
            Log.d("JSONPlaceholder", "2. appDao.getFavoritePhotos() -> " + appDao.getFavoritePhotos());//todo
            refresh(albumId);
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
    private void refresh(final int albumId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            final List<Photo> updatedPhotos = appDao.getPhotos(albumId);
            for(Photo photo : updatedPhotos)
                Log.d("JSONPlaceholder", "Photo: " + photo.isFavorite() + " | " + photo.getId() + " | " + photo.getTitle());//todo delete
            photos.clear();
            photos.addAll(updatedPhotos);
            runOnUiThread(() -> photosRecyclerAdapter.notifyDataSetChanged());
        });
    }

    /**
     * This is a callback method, defined in the implemented interface:
     * PhotosRecyclerAdapter.OnFavoriteChangedListener
     * This method is called whenever a change is actioned to the 'favorite' status of any of the
     * displayed photos.
     *
     * @param position the position in the list of the affected photo
     * @param favorite the new value
     */
    @Override
    public void onFavoriteChanged(int position, boolean favorite) {
        // handle checks/unchecks of favorites

        // first update the local structure
        final Photo oldPhoto = photos.elementAt(position);
        photos.removeElementAt(position);
        final Photo updatedPhoto = new Photo(oldPhoto.getId(), oldPhoto.getAlbumId(), oldPhoto.getTitle(), oldPhoto.getUrl(), oldPhoto.getThumbnailUrl(), favorite);
        photos.add(position, updatedPhoto);
        photosRecyclerAdapter.notifyItemChanged(position);

        // next update the database / run on a separate thread
        Log.d("JSONPlaceholder", "Adding updated photo: " + updatedPhoto.isFavorite() + " | " + updatedPhoto.getId() + " | " + updatedPhoto.getTitle());//todo delete
        Executors.newSingleThreadExecutor().execute(() -> appDao.update(updatedPhoto));
    }
}