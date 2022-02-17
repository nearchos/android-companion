package com.aspectsense.jsonplaceholderapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Realizes the DAO for our Room database.
 * For each of the two model classes (Album and Photo), it provides the CRUD operations.
 * For adding new data, the 'insert' methods have been marked to ignore an update if the
 * corresponding data already exists in the database.
 */
@Dao
interface AppDao {

    /** Gets all albums - albums are ordered by title */
    @Query("SELECT * FROM albums ORDER BY title")
    List<Album> getAlbums();

    /** Inserts specified album(s) - if not already on the database. */
    @Insert(onConflict = OnConflictStrategy.IGNORE) // skip adding items which already exist in the DB
    void insert(Album... albums);

    /** Updates specified album with given values - assumes an album with given id exists */
    @Update
    void update(Album album);

    /** Deletes specified album - assumes an album with given id exists */
    @Delete
    void delete(Album album);

    /** Gets all photos for the given album id - returned photos are ordered by title */
    @Query("SELECT * FROM photos WHERE albumId=:albumId ORDER BY title")
    List<Photo> getPhotos(final int albumId);

    /** Gets all favorite photos regardless of album id - returned photos are ordered by title */
    @Query("SELECT * FROM photos WHERE favorite=1 ORDER BY title")
    List<Photo> getFavoritePhotos();

    /** Inserts specified photo(s) - if not already on the database. */
    @Insert(onConflict = OnConflictStrategy.IGNORE) // skip adding items which already exist in the DB
    void insert(Photo... photos);

    /** Updates specified photo with given values - assumes a photo with given id exists */
    @Update
    void update(Photo photo);

    /** Deletes specified photo - assumes a photo with given id exists */
    @Delete
    void delete(Photo photo);
}