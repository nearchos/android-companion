package io.github.nearchos.testing;

import org.junit.Test;

import io.github.nearchos.testing.model.Unit;

import static org.junit.Assert.*;

/**
 * Example local unit tests, which execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final double DELTA = 0.001d; // set the required precision to 1/1000th

    @Test
    public void conversionLiters() {
        assertEquals( // 1 Liter = 4 cups ?
                Conversions.convert(Unit.CUP, Unit.ML, 4),
                Conversions.convert(Unit.L, Unit.ML, 1),
                DELTA);
    }

    @Test
    public void conversionCups() {
        assertEquals( // 1 cup = 50 teaspoons ?
                Conversions.convert(Unit.CUP, Unit.ML, 1),
                Conversions.convert(Unit.TS, Unit.ML, 50),
                DELTA
        );
    }

    @Test
    public void conversionTablespoons() {
        assertEquals( // 1 tablespoon = 3 teaspoons ?
                Conversions.convert(Unit.TB, Unit.ML, 1),
                Conversions.convert(Unit. TS, Unit.ML, 3),
                DELTA
        );
    }
}