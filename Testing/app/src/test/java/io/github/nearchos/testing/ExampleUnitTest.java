package io.github.nearchos.testing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit tests, which execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final double DELTA = 0.001d;

    @Test
    public void conversionLiters() {
        assertEquals( // 1 Liter = 4 cups ?
                Conversions.convertCupsToMl(4),
                Conversions.convertLitersToMl(1),
                DELTA);
    }

    @Test
    public void conversionCups() {
        assertEquals( // 1 cup = 50 teaspoons ?
                Conversions.convertCupsToMl(1),
                Conversions.convertTeaspoonToMl(50),
                DELTA
        );
    }

    @Test
    public void conversionTablespoons() {
        assertEquals( // 1 tablespoon = 3 teaspoons ?
                Conversions.convertTablespoonToMl(1),
                Conversions.convertTeaspoonToMl(3),
                DELTA
        );
    }
}