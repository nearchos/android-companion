package com.aspectsense.jsonplaceholderapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * A standard implementation of a Room database.
 * Utilizes the standard singleton pattern as advised by Android developers:
 * https://developer.android.com/training/data-storage/room
 */
@Database(entities = {Album.class, Photo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

   public abstract AppDao appDao();

   private static volatile AppDatabase INSTANCE;

   public static AppDatabase getDatabase(final Context context) {
      if (INSTANCE == null) {
         synchronized (AppDatabase.class) {
            if (INSTANCE == null) {
               INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                       AppDatabase.class, "json_placeholder_database") // name of the actual file where data will be stored
                       .build();
            }
         }
      }
      return INSTANCE;
   }

}
