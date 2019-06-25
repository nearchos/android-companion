package io.github.nearchos.imagesdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "raw_images")
public class RawImageEntry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    private long timestamp;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte [] image;

    private String name;
    private int size;

    public RawImageEntry(String name, byte [] image) {
        this.timestamp = System.currentTimeMillis();
        this.image = image;
        this.name = name;
        this.size = image.length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (%d bytes) created %tc", name, size, timestamp);
    }
}