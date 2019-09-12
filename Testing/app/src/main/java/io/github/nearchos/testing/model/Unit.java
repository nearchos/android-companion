package io.github.nearchos.testing.model;

public enum Unit {

    EACH("each", "each", UnitType.ENUMERABLE),
    DOZEN("dozen", "dz", UnitType.ENUMERABLE), // e.g. of eggs
    BATCH("batch", "batch", UnitType.ENUMERABLE), // e.g. of celery

    GR("gram", "g", UnitType.WEIGHT),
    KGR("kilogram", "Kg", UnitType.WEIGHT),
    LB("pound/libra", "Lb", UnitType.WEIGHT),
    OZ("Ounce", "oz", UnitType.WEIGHT),

    L("liter", "L", UnitType.VOLUME),
    ML("milliliter", "mL", UnitType.VOLUME),
    CUP("cup", "cup", UnitType.VOLUME), // 250 mL
    TB("tablespoon", "Tbsp.", UnitType.VOLUME), // 15 mL
    TS("teaspoon", "tsp.", UnitType.VOLUME); // 5 mL

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