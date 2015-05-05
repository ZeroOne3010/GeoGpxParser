package zeroone3010.geogpxparser;

import static java.util.function.Function.identity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum LogType {

    FOUND("Found it"), ATTENDED("Attended"), WEBCAM_PHOTO_TAKEN("Webcam Photo Taken"), DNF("Didn't find it"), OTHER(null);
    private final String gpxDescription;
    private static final Map<String, LogType> gpxToType;

    static {
        gpxToType = Arrays.asList(LogType.values()).stream()
                .collect(Collectors.toMap(LogType::getGpxDescription, identity()));
    }

    LogType(final String gpxText) {
        this.gpxDescription = gpxText;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static LogType getByGpxDescription(final String description) {
        return Optional.ofNullable(gpxToType.get(description)).orElse(OTHER);
    }

    public boolean countsAsFind() {
        return this == FOUND || this == ATTENDED || this == WEBCAM_PHOTO_TAKEN;
    }
}
