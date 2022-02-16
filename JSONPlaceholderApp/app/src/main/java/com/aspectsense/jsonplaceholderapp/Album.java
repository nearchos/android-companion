package com.aspectsense.jsonplaceholderapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "albums")
class Album {

   @PrimaryKey final int id;
   final int userId;
   final String title;

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

   @NonNull
   @Override
   public String toString() {
      return title;
   }
}