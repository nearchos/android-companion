package io.github.nearchos.imagesdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_ACTION_CODE = 42;

    private ListView listView;

    private ImagesRoomDatabase imagesRoomDatabase;

    private List<ImageEntry> imageEntries = new Vector<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Bitmap bitmap = getBitmapFromUri(uri);

                    ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setImageBitmap(bitmap);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Image")
                            .setView(imageView)
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create()
                            .show();
                } catch (IOException ioe) {
                    Toast.makeText(MainActivity.this, "ERROR!\n" + ioe.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addPicture(View view) {

        Intent pickIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_ACTION_CODE);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE_ACTION_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "data: " + data, Toast.LENGTH_SHORT).show();
            Log.d("CO4755", "data: " + data);

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