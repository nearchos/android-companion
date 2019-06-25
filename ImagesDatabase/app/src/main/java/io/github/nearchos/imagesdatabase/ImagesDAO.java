package io.github.nearchos.imagesdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImagesDAO {

    @Insert
    void insert(ImageEntry imageEntry);

    @Query("SELECT * FROM images ORDER BY timestamp DESC")
    List<ImageEntry> getAllImages();

    @Insert
    void insert(RawImageEntry rawImageEntry);

    @Query("SELECT * FROM raw_images ORDER BY timestamp DESC")
    List<RawImageEntry> getAllRawImages();
}