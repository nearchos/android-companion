package io.github.nearchos.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BlogDao {

    @Insert
    void insert(Entry entry);

    @Query("SELECT * FROM entries ORDER BY timestamp")
    List<Entry> getAllEntries();

    @Delete
    void delete(Entry entry);
}