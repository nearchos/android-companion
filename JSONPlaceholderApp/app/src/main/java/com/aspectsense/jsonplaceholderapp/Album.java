package com.aspectsense.jsonplaceholderapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Models an 'album' object similar to those encoded in JSON and returned by JSONPlaceholder:
 * https://jsonplaceholder.typicode.com/albums
 * The class is implemented as immutable: can only be initialized at construction, and its values
 * are read-only.
 */
@Entity(tableName = "albums") // annotated so it can be stored in Room
class Album {

   @PrimaryKey private final int id; // marked as Primary Key for Room-purposes
   private final int userId;
   private final String title;

   public Album(int id, int userId, String title) {
      this.id = id;
      this.userId = userId;
      this.title = title;
   }

   public int getId() {
      return id;
   }

   public int getUserId() {
      return userId;
   }

   public String getTitle() {
      return title;
   }

   /**
    * Override toString as to return the more human-friendly title of the album.
    * @return the title of the album
    */
   @NonNull
   @Override
   public String toString() {
      return title;
   }
}