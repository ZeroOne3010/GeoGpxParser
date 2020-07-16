package io.github.zeroone3010.geogpxparser.coordinateformatters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DegreesAndMinutesFormatterTest {
    final DegreesAndMinutesFormatter formatter = new DegreesAndMinutesFormatter();

    @Test
    public void test_northern_latitudeString() {
        assertEquals("N 60° 10.123'", formatter.formatLatitude(60.16872));
    }

    @Test
    public void test_southern_latitudeString() {
        assertEquals("S 09° 09.999'", formatter.formatLatitude(-9.16665));
    }

    @Test
    public void test_eastern_longitudeString() {
        assertEquals("E 24° 04.000'", formatter.formatLongitude(24.06667));
    }

    @Test
    public void test_western_longitudeString() {
        assertEquals("W 100° 12.345'", formatter.formatLongitude(-100.20575));
    }
}
