package io.github.nearchos.testing;

import java.util.HashMap;
import java.util.Map;

import io.github.nearchos.testing.model.Unit;

public class Conversions {

    /**
     * Returns the quantity converted from the source {@link Unit} to the target {@link Unit}.
     * For example, when the source {@link Unit} is {@link Unit#KGR} and the target is
     * {@link Unit#GR}, then the source quantity is multiplied by 1000, e.g. 2 {@link Unit#KGR}s
     * equals 2000 {@link Unit#GR}s.
     *
     * @param source the source {@link Unit} to convert from
     * @param target the target {@link Unit} to convert to
     * @param quantity the quantity of the source {@link Unit}
     * @return the computed quantity of the target {@link Unit}
     */
    static Double convert(Unit source, Unit target, double quantity) {
        if(source == null || target == null)
            throw new NullPointerException("Invalid null argument");
        if(source.getUnitType() != target.getUnitType())
            throw new IllegalArgumentException("Incompatible conversion from " + source + " to " + target);

        final Double ratio = RATIOS.get(new UnitPair(source, target));
        return ratio == null ? Double.NaN : ratio * quantity;
    }

    private static final Map<UnitPair, Double> RATIOS = new HashMap<UnitPair, Double>() {{
        put(new UnitPair(Unit.KGR, Unit.GR), 1000d);
        put(new UnitPair(Unit.KGR, Unit.LB), 2.20462d);
        put(new UnitPair(Unit.KGR, Unit.OZ), 35.2739199982575d);

        put(new UnitPair(Unit.GR, Unit.KGR), 0.001d);
        put(new UnitPair(Unit.GR, Unit.LB), 0.00220462d);
        put(new UnitPair(Unit.GR, Unit.OZ), 0.035274d);

        put(new UnitPair(Unit.LB, Unit.KGR), 0.453592d);
        put(new UnitPair(Unit.LB, Unit.GR), 453.592d);
        put(new UnitPair(Unit.LB, Unit.OZ), 16d);

        put(new UnitPair(Unit.OZ, Unit.KGR), 0.0283495d);
        put(new UnitPair(Unit.OZ, Unit.GR), 28.3495d);
        put(new UnitPair(Unit.OZ, Unit.LB), 0.0625d);


        put(new UnitPair(Unit.L, Unit.ML), 1000d);
        put(new UnitPair(Unit.L, Unit.CUP), 4d);
        put(new UnitPair(Unit.L, Unit.TB), 66.67d);
        put(new UnitPair(Unit.L, Unit.TS), 200d);

        put(new UnitPair(Unit.ML, Unit.L), 0.001d);
        put(new UnitPair(Unit.ML, Unit.CUP), 0.004d);
        put(new UnitPair(Unit.ML, Unit.TB), 0.0667d);
        put(new UnitPair(Unit.ML, Unit.TS), 0.2d);

        put(new UnitPair(Unit.CUP, Unit.L), 0.25d);
        put(new UnitPair(Unit.CUP, Unit.ML), 250d);
        put(new UnitPair(Unit.CUP, Unit.TB), 16.67d);
        put(new UnitPair(Unit.CUP, Unit.TS), 50d);

        put(new UnitPair(Unit.TB, Unit.L), 0.015d);
        put(new UnitPair(Unit.TB, Unit.ML), 15d);
        put(new UnitPair(Unit.TB, Unit.CUP), 0.06d);
        put(new UnitPair(Unit.TB, Unit.TS), 3d);

        put(new UnitPair(Unit.TS, Unit.L), 0.005d);
        put(new UnitPair(Unit.TS, Unit.ML), 5d);
        put(new UnitPair(Unit.TS, Unit.CUP), 0.02d);
        put(new UnitPair(Unit.TS, Unit.TB), 0.333d);
    }};

    private static class UnitPair {
        Unit fromUnit;
        Unit toUnit;

        UnitPair(Unit fromUnit, Unit toUnit) {
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            UnitPair unitPair = (UnitPair) other;
            return fromUnit == unitPair.fromUnit && toUnit == unitPair.toUnit;
        }

        @Override
        public int hashCode() {
            return fromUnit.hashCode() * 17 + toUnit.hashCode();
        }
    }
}