package io.github.nearchos.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Entry.class}, version = 1)
public abstract class BlogRoomDatabase extends RoomDatabase {

    public abstract BlogDao blogDao();

    private static volatile BlogRoomDatabase INSTANCE;

    public static BlogRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BlogRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BlogRoomDatabase.class, "blog_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}