package io.github.nearchos.testing;

import android.arch.persistence.room.TypeConverter;

import io.github.nearchos.testing.model.Category;
import io.github.nearchos.testing.model.Unit;
import io.github.nearchos.testing.model.UnitType;

public class DatabaseConverters {

    // handling Category conversions for DB save/load

    @TypeConverter
    public static Category toCategory(int ordinal) {
        return Category.values()[ordinal];
    }

    @TypeConverter
    public static int toInteger(Category category) {
        return category.ordinal();
    }

    // handling Unit conversions for DB save/load

    @TypeConverter
    public static Unit toUnit(int ordinal) {
        return Unit.values()[ordinal];
    }

    @TypeConverter
    public static int toInteger(Unit unit) {
        return unit.ordinal();
    }

    // handling UnitType conversions for DB save/load

    @TypeConverter
    public static UnitType toUnitType(int ordinal) {
        return UnitType.values()[ordinal];
    }

    @TypeConverter
    public static int toInteger(UnitType unitType) {
        return unitType.ordinal();
    }
}