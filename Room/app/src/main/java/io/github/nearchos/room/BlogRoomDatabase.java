package io.github.nearchos.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Entry.class}, version = 1, exportSchema = false)
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