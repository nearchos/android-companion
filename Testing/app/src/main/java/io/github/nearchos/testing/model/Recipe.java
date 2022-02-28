package io.github.nearchos.testing.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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

    /**
     * Overloaded default method to create proper text-representation to be used in list adapter.
     * @return a string representation of the recipe, via its name and preparation time
     */
    @NonNull
    @Override
    public String toString() {
        return name + " (" + preparationTimeInMinutes + " minutes)";
    }
}