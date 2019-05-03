package io.github.nearchos.testing.model;

/**
 * @author Nearchos
 * Created: 24-Apr-19
 */
public enum Unit {

    EACH("each", "each", UnitType.ENUMERABLE),
    BATCH("batch", "batch", UnitType.ENUMERABLE), // e.g. of celery
    DOZEN("dozen", "dozen", UnitType.ENUMERABLE), // e.g. of eggs
    GR("gram", "g", UnitType.WEIGHT),
    KGR("kilogram", "Kg", UnitType.WEIGHT),
    ML("milliliter", "mL", UnitType.VOLUME),
    L("liter", "L", UnitType.VOLUME),
    CUP("cup", "cup", UnitType.VOLUME), // 250 mL
    TB("tablespoon", "tbsp", UnitType.VOLUME), // 15 mL
    TS("teaspoon", "ts", UnitType.VOLUME); // 5 mL

    private String fullName;
    private String shortName;
    private UnitType unitType;

    Unit(String fullName, String shortName, UnitType unitType) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.unitType = unitType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public UnitType getUnitType() {
        return unitType;
    }


    @Override
    public String toString() {
        return fullName + " (" + shortName + ")";
    }
}