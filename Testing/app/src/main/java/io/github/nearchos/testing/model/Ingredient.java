package io.github.nearchos.testing.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ingredients")
public class Ingredient implements Serializable {

    @PrimaryKey(autoGenerate = true) private long _id;
    private String name;
    private Category category;
    private UnitType unitType;

    @Ignore // used for testing only
    public Ingredient(String name, Category category, UnitType unitType) {
        this.name = name;
        this.category = category;
        this.unitType = unitType;
    }

    public Ingredient(long _id, String name, Category category, UnitType unitType) {
        this._id = _id;
        this.name = name;
        this.category = category;
        this.unitType = unitType;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * Overloaded default method to create proper text-representation to be used in list adapter.
     * @return a string representation of the ingredient, via its name
     */
    @Override
    public String toString() {
        return name;
    }
}