package io.github.nearchos.imagesdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ImageEntry.class, RawImageEntry.class}, version = 1)
public abstract class ImagesRoomDatabase extends RoomDatabase {

    public abstract ImagesDAO imagesDao();

    private static volatile ImagesRoomDatabase INSTANCE;

    public static ImagesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ImagesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ImagesRoomDatabase.class, "images_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}