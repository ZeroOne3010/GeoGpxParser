package zeroone3010.geogpxparser.coordinateformatters;

import java.util.Locale;

public class DegreesAndMinutesFormatter implements CoordinateFormatter {

    @Override
    public String formatLatitude(double latitude) {
        return getCoordinateInDegreesAndMinutes('N', 'S', latitude);
    }

    @Override
    public String formatLongitude(double longitude) {
        return getCoordinateInDegreesAndMinutes('E', 'W', longitude);
    }

    private static String getCoordinateInDegreesAndMinutes(char posLetter, char negLetter, double value) {
        final int degrees = Math.abs((int) value);
        final double minutes = (Math.abs(value) - degrees) * 60d;
        final char letter = value >= 0 ? posLetter : negLetter;
        return String.format(Locale.US, "%c %02d° %06.3f'", letter, degrees, minutes);
    }
}
