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

import java.io.FileDescriptor;
import java.io.IOException;

class ImageDialog {

    private static Bitmap getBitmapFromUri(final Uri uri, final ContentResolver contentResolver) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    static void showImageDialog(final Context context, final Uri imageUri) throws IOException {
        final Bitmap bitmap = getBitmapFromUri(imageUri, context.getContentResolver());
        final ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);

        new AlertDialog.Builder(context)
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
    }

    static void showImageDialog(final Context context, final byte [] bytes) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);;
        final ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);

        new AlertDialog.Builder(context)
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
    }
}