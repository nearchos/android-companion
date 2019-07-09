package io.github.nearchos.imagesdatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private ImagesRoomDatabase imagesRoomDatabase;

    private List<RawImageEntry> imageEntries = new Vector<>();
    private ArrayAdapter<RawImageEntry> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_images);

        imagesRoomDatabase = ImagesRoomDatabase.getDatabase(this);

        final ListView listView = findViewById(R.id.listView);
        imageEntries = imagesRoomDatabase.imagesDao().getAllRawImages();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imageEntries);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            // access uri (as String), convert it to a Uri and then form a Bitmap to show in an ImageView
            byte [] bytes = imageEntries.get(i).getImage();
            ImageDialog.showImageDialog(RawImagesActivity.this, bytes);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_ACTION_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if(uri != null) {
                final byte [] original = getBytes(uri); // get original bitmap
                final byte [] bytes = resizeBitmap(original, 0.5f); // resize to 50%
                final String name = uri.getLastPathSegment(); // get name from path
                RawImageEntry rawImageEntry = new RawImageEntry(name, bytes);
                imagesRoomDatabase.imagesDao().insert(rawImageEntry);
                imageEntries.add(0, rawImageEntry); // add it first
                arrayAdapter.notifyDataSetChanged(); // update ListView
            }
        }
    }

    byte [] getBytes(final Uri uri) {
        try {
            final InputStream inputStream = getContentResolver().openInputStream(uri);
            final ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            byte [] buffer = new byte[1024]; // buffer size = 1024

            int len; // get all bytes...
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe); // handle IO exception
        }
    }

    byte [] resizeBitmap(final byte [] bytes, float scale) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // resize configuration

        final Bitmap resizedBitmap = // resize bitmap
                Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte [] byteArray = byteArrayOutputStream.toByteArray();
        resizedBitmap.recycle();

        return byteArray;
    }
}