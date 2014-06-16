package zeroone3010.geogpxparser.coordinateformatters;

public interface CoordinateFormatter {
    String formatLatitude(double latitude);

    String formatLongitude(double longitude);
}
