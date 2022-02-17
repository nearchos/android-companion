package com.aspectsense.jsonplaceholderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Vector;
import java.util.concurrent.Executors;

/**
 * The starting activity (screen) of the app.
 * This shows the 'favorite' photos only.
 * Initially it's empty. You can add favorite photos by selecting the '+' floating action button,
 * and choosing an album, then marking photos as favorite.
 * All data is stored in Room database and ia available for offline use.
 */
public class MainActivity extends AppCompatActivity implements PhotosRecyclerAdapter.OnFavoriteChangedListener {

    // contains the favorite photos to be listed - initially empty
    private final Vector<Photo> favoritePhotos = new Vector<>();

    // The DAO used to access the Room database
    private AppDao appDao;

    private View root;

    private PhotosRecyclerAdapter favoritePhotosRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addFavoritesButton).setOnClickListener(view -> startActivity(new Intent(this, AlbumsActivity.class)));

        this.appDao = AppDatabase.getDatabase(this).appDao();

        root = findViewById(R.id.activity_main_root);

        RecyclerView favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        favoriteRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(favoriteRecyclerView.getContext(), linearLayoutManager.getOrientation());
        favoriteRecyclerView.addItemDecoration(dividerItemDecoration);

        favoritePhotosRecyclerAdapter = new PhotosRecyclerAdapter(getApplicationContext(), favoritePhotos, this);
        favoriteRecyclerView.setAdapter(favoritePhotosRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // read favorite photos from the database - must run on a separate thread
        Executors.newSingleThreadExecutor().execute(() -> {
            favoritePhotos.clear();
            favoritePhotos.addAll(appDao.getFavoritePhotos());
            runOnUiThread(() -> {
                // notify that the list of photos has changed
                favoritePhotosRecyclerAdapter.notifyDataSetChanged();
                // show a snack if no favorite photos exist
                if(favoritePhotos.isEmpty()) {
                    Snackbar.make(root, "No favorites yet!", Snackbar.LENGTH_SHORT).show();
                }
            });
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
        // handle unchecks of favorites
        assert !favorite; // favorite must always be false because this list has favorite photos only

        // first update the local list
        final Photo removedPhoto = favoritePhotos.elementAt(position);
        favoritePhotos.removeElementAt(position);
        favoritePhotosRecyclerAdapter.notifyDataSetChanged();

        // create a copy of this photo, where the 'favorite' value is false
        final Photo updatedPhoto = new Photo(removedPhoto.getId(), removedPhoto.getAlbumId(), removedPhoto.getTitle(), removedPhoto.getUrl(), removedPhoto.getThumbnailUrl(), false);
        // next update the database - must run on a separate thread
        Executors.newSingleThreadExecutor().execute(() -> appDao.update(updatedPhoto));

        // last provide a snack update with an option to Undo
        Snackbar.make(root, "Photo deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("Undo", view -> undo(removedPhoto, position)).show();
    }

    /**
     * This function is called if the user selected to 'undo' the un-favoring of a photo.
     * The result is to re-insert the photo in the list at its original position, and to also
     * re-insert it in the Room database.
     *
     * @param restoredPhoto the photo to be restored
     * @param position the position where the photo should be restored
     */
    private void undo(final Photo restoredPhoto, final int position) {
        favoritePhotos.add(position, restoredPhoto); // update the local list...
        favoritePhotosRecyclerAdapter.notifyDataSetChanged(); // ..and notify the adapter to update
        Executors.newSingleThreadExecutor().execute(() -> appDao.update(restoredPhoto)); // re-insert in database
    }
}