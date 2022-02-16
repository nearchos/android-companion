package com.aspectsense.jsonplaceholderapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
class Photo {

   @PrimaryKey private final int id;
   final int albumId;
   final String title;
   final String url;
   final String thumbnailUrl;

   public Photo(int id, int albumId, String title, String url, String thumbnailUrl) {
      this.id = id;
      this.albumId = albumId;
      this.title = title;
      this.url = url;
      this.thumbnailUrl = thumbnailUrl;
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

   @NonNull
   @Override
   public String toString() {
      return title;
   }
}