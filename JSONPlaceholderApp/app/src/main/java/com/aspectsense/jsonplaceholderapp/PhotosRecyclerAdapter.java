package com.aspectsense.jsonplaceholderapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.Vector;

/**
 * Implements a custom RecyclerView.Adapter for displaying photo items, with a thumbnail, title,
 * and a custom 'favorite' checkbox.
 * It also defines a custom OnFavoriteChangedListener interface for realizing the observer pattern.
 */
class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoViewHolder> {

   /**
    * Holds the widget views of one item in the photos list.
    */
   static class PhotoViewHolder extends RecyclerView.ViewHolder {

      final ImageView thumbnailImageView;
      final CheckBox favoriteCheckBox;
      final TextView titleTextView;

      public PhotoViewHolder(@NonNull View photoItemView) {
         super(photoItemView);
         thumbnailImageView = photoItemView.findViewById(R.id.thumbnailImageView);
         favoriteCheckBox = photoItemView.findViewById(R.id.favoriteCheckbox);
         titleTextView = photoItemView.findViewById(R.id.titleTextView);
      }
   }

   private final Context applicationContext;
   private final Vector<Photo> photos;
   private final OnFavoriteChangedListener onFavoriteChangedListener;

   PhotosRecyclerAdapter(final Context applicationContext, final Vector<Photo> photos, final OnFavoriteChangedListener onFavoriteChangedListener) {
      this.applicationContext = applicationContext;
      this.photos = photos;
      this.onFavoriteChangedListener = onFavoriteChangedListener;
   }

   // Creates new views (invoked by the layout manager)
   @NonNull
   @Override
   public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      // create a new view
      final View view = LayoutInflater
              .from(parent.getContext())
              .inflate(R.layout.photo_item_view, parent, false);

      return new PhotoViewHolder(view);
   }

   // Replaces the contents of a view (invoked by the layout manager)
   @Override
   public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
      final Photo photo = photos.get(position);
      holder.titleTextView.setText(photo.getTitle());
      holder.favoriteCheckBox.setOnCheckedChangeListener(null); // first unregister any previous listeners
      if(photo.isFavorite()) Log.d("JSONPlaceholder", "DING! -> "+ photo);//todo
      holder.favoriteCheckBox.setChecked(photo.isFavorite()); // then set the favorite status
      holder.favoriteCheckBox.setOnCheckedChangeListener((compoundButton, b) -> onFavoriteChangedListener.onFavoriteChanged(position, b)); // finally start listening for changes again
      final String myUserAgent = WebSettings.getDefaultUserAgent(applicationContext);
      final GlideUrl url = new GlideUrl(
              photo.getThumbnailUrl(),
              new LazyHeaders.Builder().addHeader("User-Agent", myUserAgent).build());
      Glide.with(applicationContext)
              .load(url)
              .circleCrop()
              .into(holder.thumbnailImageView);
   }

   @Override
   public int getItemCount() {
      return photos.size();
   }

   /**
    * The listener interface used to realize the observer pattern.
    */
   interface OnFavoriteChangedListener {
      void onFavoriteChanged(final int position, final boolean favorite);
   }
}