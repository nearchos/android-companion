package io.github.nearchos.imagesdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Locale;

@Entity(tableName = "images")
public class ImageEntry implements Serializable {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") private int id;
    private long timestamp = System.currentTimeMillis();
    private String uri;

    public ImageEntry(String uri) {
        this.uri = uri;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    @Override public String toString() {
        return String.format(Locale.US, "%tc: %s", timestamp, uri);
    }
}