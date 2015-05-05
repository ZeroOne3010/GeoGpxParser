package zeroone3010.geogpxparser.coordinateformatters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultCoordinateFormatterTest {
    @Test
    public void should_return_latitude_with_6_decimals() {
        final DefaultCoordinateFormatter formatter = new DefaultCoordinateFormatter();
        assertEquals("1.234568", formatter.formatLatitude(1.23456798d));
        assertEquals("-20.304000", formatter.formatLatitude(-20.3040d));
        assertEquals("80.000000", formatter.formatLatitude(80.00d));
    }

    @Test
    public void should_return_longitude_with_6_decimals() {
        final DefaultCoordinateFormatter formatter = new DefaultCoordinateFormatter();
        assertEquals("1.234568", formatter.formatLongitude(1.2345678d));
        assertEquals("-20.304000", formatter.formatLongitude(-20.3040d));
        assertEquals("130.000000", formatter.formatLongitude(130.00d));
    }
}
