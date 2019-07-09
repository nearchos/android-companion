package io.github.nearchos.imagesdatabase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Vector;

public class UriImagesActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_ACTION_CODE = 42;

    private ImagesRoomDatabase imagesRoomDatabase;

    private List<ImageEntry> imageEntries = new Vector<>();
    private ArrayAdapter<ImageEntry> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri_images);

        imagesRoomDatabase = ImagesRoomDatabase.getDatabase(this);

        final ListView listView = findViewById(R.id.listView);
        imageEntries = imagesRoomDatabase.imagesDao().getAllImages();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imageEntries);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            // access uri (as String), convert it to a Uri and then form a Bitmap to show in an ImageView
            String uriSerialized = imageEntries.get(i).getUri();
            Uri uri = Uri.parse(uriSerialized);
            ImageDialog.showImageDialog(UriImagesActivity.this, uri);
        });
    }

    public void addImage(View view) {

        Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_ACTION_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_ACTION_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if(uri != null) { // the following line is important as otherwise the URI will be one-time use (instead of permanent) - requires min api 19
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); // requires min api 19
                ImageEntry imageEntry = new ImageEntry(uri.toString());
                imagesRoomDatabase.imagesDao().insert(imageEntry);
                imageEntries.add(0, imageEntry); // add it first
                arrayAdapter.notifyDataSetChanged(); // update ListView
            }
        }
    }
}