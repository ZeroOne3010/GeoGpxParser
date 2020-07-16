package io.github.zeroone3010.geogpxparser.coordinateformatters;

import java.util.Locale;

public class DegreesAndMinutesFormatter implements CoordinateFormatter {

    @Override
    public String formatLatitude(final double latitude) {
        return getCoordinateInDegreesAndMinutes('N', 'S', latitude);
    }

    @Override
    public String formatLongitude(final double longitude) {
        return getCoordinateInDegreesAndMinutes('E', 'W', longitude);
    }

    private static String getCoordinateInDegreesAndMinutes(final char posLetter, final char negLetter, final double value) {
        final int degrees = Math.abs((int) value);
        final double minutes = (Math.abs(value) - degrees) * 60d;
        final char letter = value >= 0 ? posLetter : negLetter;
        return String.format(Locale.US, "%c %02d° %06.3f'", letter, degrees, minutes);
    }
}
