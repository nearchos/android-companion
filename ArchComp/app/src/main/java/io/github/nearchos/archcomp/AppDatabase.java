package io.github.nearchos.archcomp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.nearchos.archcomp.MainActivity.TAG;

/**
 * @author Nearchos
 * Created: 14-Jan-20
 */
@Database(entities = {GithubFollower.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context, AppDatabase.class, "github_api")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // executor service used for running DB operations on the background
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "Creating DB!");
            // run using a background thread (via 'concurrent' Java lib: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Executors.html)
            executorService.execute(() -> INSTANCE.appDao().insert(DEFAULT_GITHUB_FOLLOWER));
        }
    };

    private static final GithubFollower DEFAULT_GITHUB_FOLLOWER = new GithubFollower(
            583231L,
            "octocat",
            "https://avatars3.githubusercontent.com/u/583231?v=4",
            "https://api.github.com/users/octocat/repos",
            false
    );

}