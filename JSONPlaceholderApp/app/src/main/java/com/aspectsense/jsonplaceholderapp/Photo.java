package com.aspectsense.jsonplaceholderapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Models a 'photo' object similar to those encoded in JSON and returned by JSONPlaceholder:
 * https://jsonplaceholder.typicode.com/photos
 * The class is implemented as immutable: can only be initialized at construction, and its values
 * are read-only.
 * Important: Even though the JSONPlaceholder object does not define a 'favorite' value, this works
 * OK. The 'favorite' is initialized to its default value of 'false'.
 */
@Entity(tableName = "photos")
class Photo {

   @PrimaryKey private final int id;
   private final int albumId;
   private final String title;
   private final String url;
   private final String thumbnailUrl;
   private final boolean favorite;

   public Photo(int id, int albumId, String title, String url, String thumbnailUrl, boolean favorite) {
      this.id = id;
      this.albumId = albumId;
      this.title = title;
      this.url = url;
      this.thumbnailUrl = thumbnailUrl;
      this.favorite = favorite;
   }

   public int getId() {
      return id;
   }

   public int getAlbumId() {
      return albumId;
   }

   public String getTitle() {
      return title;
   }

   public String getUrl() {
      return url;
   }

   public String getThumbnailUrl() {
      return thumbnailUrl;
   }

   public boolean isFavorite() {
      return favorite;
   }

   @NonNull
   @Override
   public String toString() {
      return title;
   }
}