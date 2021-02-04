package io.github.nearchos.volley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.Vector;

/**
 * @author Nearchos Paspallis
 * Created: 16-Jan-21
 */
class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewTitle;
        public ImageView imageViewThumbnail;
        public ViewHolder(View view) {
            super(view);
            this.textViewTitle = view.findViewById(R.id.textViewTitle);
            this.imageViewThumbnail = view.findViewById(R.id.imageViewThumbnail);
        }
    }

    private final Context applicationContext;
    private final Vector<Photo> photos;

    public PhotosAdapter(final Context context, final Vector<Photo> photos) {
        this.applicationContext = context.getApplicationContext();
        this.photos = photos;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.photo_item_view, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Photo photo = photos.get(position);
        holder.textViewTitle.setText(photo.getTitle());
//        holder.imageViewThumbnail.setImageURI(Uri.parse(photo.getThumbnailUrl()));
        final String myUserAgent = WebSettings.getDefaultUserAgent(applicationContext);
        final GlideUrl url = new GlideUrl(photo.getThumbnailUrl(), new LazyHeaders.Builder().addHeader("User-Agent", myUserAgent).build());
        Glide.with(applicationContext)
                .load(url)
                .centerCrop()
                .placeholder(new CircularProgressDrawable(applicationContext))
                .error(R.drawable.loading_error)
                .into(holder.imageViewThumbnail);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return photos.size();
    }
}