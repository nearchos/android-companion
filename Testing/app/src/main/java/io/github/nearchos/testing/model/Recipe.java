package io.github.nearchos.testing.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author Nearchos
 * Created: 24-Apr-19
 */
@Entity(tableName = "recipes")
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true) private long _id;
    private String name;
    private String description;
    private long creationTimestamp;
    private int preparationTimeInMinutes;

    public Recipe(long _id, String name, String description, long creationTimestamp, int preparationTimeInMinutes) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.creationTimestamp = creationTimestamp;
        this.preparationTimeInMinutes = preparationTimeInMinutes;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public int getPreparationTimeInMinutes() {
        return preparationTimeInMinutes;
    }

    @Override
    public String toString() {
        final int limitChars = 24;
        final String truncatedDescription = description.length() > limitChars + 3 ? description.substring(0, limitChars) + "..." : description;
        return name + " (" + preparationTimeInMinutes + " m)" + "\n" + truncatedDescription;
    }
}