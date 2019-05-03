package io.github.nearchos.testing.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Nearchos
 * Created: 24-Apr-19
 */
@Entity(tableName = "ingredients")
public class Ingredient {

    @PrimaryKey(autoGenerate = true) private long _id;
    private String name;
    private Category category;
    private UnitType unitType;

    @Ignore // used for testing only
    Ingredient(String name, Category category, UnitType unitType) {
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

    @Override
    public String toString() {
        return name;
    }
}