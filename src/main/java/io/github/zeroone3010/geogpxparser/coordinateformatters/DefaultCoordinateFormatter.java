package io.github.zeroone3010.geogpxparser.coordinateformatters;

import java.util.Locale;

public class DefaultCoordinateFormatter implements CoordinateFormatter {

    @Override
    public String formatLatitude(final double latitude) {
        return String.format(Locale.US, "%.6f", latitude);
    }

    @Override
    public String formatLongitude(final double longitude) {
        return String.format(Locale.US, "%.6f", longitude);
    }

}
