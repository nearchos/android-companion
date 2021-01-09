/*
 * Copyright (c) 2020. Nearchos Paspallis
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 United
 * States License, described at this URL: https://creativecommons.org/licenses/by-nc-nd/3.0/us/.
 * Unless required by applicable law or agreed to in writing, code listed in this site is
 * distributed on "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 */

package io.github.nearchos.archcomp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GithubFollower.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context, AppDatabase.class, "github_followers")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}