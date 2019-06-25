package io.github.nearchos.imagesdatabase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class ImagesActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_ACTION_CODE = 42;

    private ListView listView;

    private ImagesRoomDatabase imagesRoomDatabase;

    private List<ImageEntry> imageEntries = new Vector<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        imagesRoomDatabase = ImagesRoomDatabase.getDatabase(this);

        listView = findViewById(R.id.listView);
        imageEntries = imagesRoomDatabase.imagesDao().getAllImages();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imageEntries);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // access uri (as String), convert it to a Uri and then form a Bitmap to show in an ImageView
                String uriSerialized = imageEntries.get(i).getUri();
                Uri uri = Uri.parse(uriSerialized);
                try {
                    ImageDialog.showImageDialog(ImagesActivity.this, uri);
                } catch (IOException ioe) {
                    Toast.makeText(ImagesActivity.this, "ERROR!\n" + ioe.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE_ACTION_CODE && resultCode == RESULT_OK) {

            Toast.makeText(this, "data: " + data, Toast.LENGTH_SHORT).show();

            Uri uri = data.getData();
            if(uri != null) {
                // the below line is important as otherwise the URI will be one-time use (instead of permanent)
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); // requires min api 19
                ImageEntry imageEntry = new ImageEntry(uri.toString());
                imagesRoomDatabase.imagesDao().insert(imageEntry);
                imageEntries.add(0, imageEntry); // add it first
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}