package io.github.nearchos.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "entries")
public class Entry implements Serializable {

    @PrimaryKey(autoGenerate = true) private int id;
    private String title;
    private String body;
    private long timestamp;

    public Entry(String title, String body) {
        this.title = title;
        this.body = body;
        // initialize with current time in UNIX format
        this.timestamp = System.currentTimeMillis();
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getBody() { return body; }

    public long getTimestamp() { return timestamp; }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return title + " - " + new Date(timestamp);
    }
}