package io.github.nearchos.imagesdatabase;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

public class RawImagesActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_ACTION_CODE = 43;

    private ListView listView;

    private ImagesRoomDatabase imagesRoomDatabase;

    private List<RawImageEntry> rawImageEntries = new Vector<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_images);

        imagesRoomDatabase = ImagesRoomDatabase.getDatabase(this);

        listView = findViewById(R.id.listView);
        rawImageEntries = imagesRoomDatabase.imagesDao().getAllRawImages();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rawImageEntries);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // access uri (as String), convert it to a Uri and then form a Bitmap to show in an ImageView
                byte [] bytes = rawImageEntries.get(i).getImage();
                ImageDialog.showImageDialog(RawImagesActivity.this, bytes);
            }
        });
    }

    public void addRawImage(View view) {

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
                try {
                    InputStream iStream = getContentResolver().openInputStream(uri);
                    final byte[] bytes = getBytes(iStream);
                    RawImageEntry rawImageEntry = new RawImageEntry(uri.getLastPathSegment(), bytes);
                    imagesRoomDatabase.imagesDao().insert(rawImageEntry);
                    rawImageEntries.add(0, rawImageEntry); // add it first
                    arrayAdapter.notifyDataSetChanged();
                } catch (IOException ioe) {
                    Toast.makeText(RawImagesActivity.this, "ERROR!\n" + ioe.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    byte [] getBytes(InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}