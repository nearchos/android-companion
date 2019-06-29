package io.github.nearchos.imagesdatabase;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

class ImageDialog {

    private static Bitmap getBitmapFromUri(final Uri uri, final ContentResolver contentResolver) throws IOException {
        final ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
        final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        final Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    static void showImageDialog(final Context context, final Uri imageUri) {
        try {
            final Bitmap bitmap = getBitmapFromUri(imageUri, context.getContentResolver());
            final ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(bitmap);

            new AlertDialog.Builder(context)
                    .setTitle("Image")
                    .setView(imageView)
                    .setPositiveButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();
        } catch (IOException ioe) {
            Toast.makeText(context, "ERROR!\n" + ioe.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    static void showImageDialog(final Context context, final byte [] bytes) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);;
        final ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);

        new AlertDialog.Builder(context)
                .setTitle("Image")
                .setView(imageView)
                .setPositiveButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show();
    }
}