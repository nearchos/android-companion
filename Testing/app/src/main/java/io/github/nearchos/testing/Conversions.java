package io.github.nearchos.testing;

public class Conversions {

    static double convertLitersToMl(double litersQuantity) {
        return 1000d * litersQuantity;
    }

    static double convertCupsToMl(double cupsQuantity) {
        return 250d * cupsQuantity;
    }

    static double convertTablespoonToMl(double tablespoonQuantity) {
        return 15d * tablespoonQuantity;
    }

    static double convertTeaspoonToMl(double teaspoonQuantity) {
        return 5d * teaspoonQuantity;
    }
}
