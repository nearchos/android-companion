package com.aspectsense.jsonplaceholderapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author Nearchos Paspallis
 * Created: 16-Feb-22
 */
@Dao
interface AppDao {

    @Query("SELECT * FROM albums ORDER BY title")
    List<Album> getAlbums();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Album... albums);

    @Update
    void update(Album album);

    @Delete
    void delete(Album album);

    @Query("SELECT * FROM photos WHERE albumId=:albumId ORDER BY title")
    List<Photo> getPhotos(final int albumId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo... photos);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);
}