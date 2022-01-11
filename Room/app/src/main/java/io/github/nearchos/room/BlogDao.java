package io.github.nearchos.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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