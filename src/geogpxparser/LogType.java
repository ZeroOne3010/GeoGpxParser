package geogpxparser;

import java.util.HashMap;
import java.util.Map;

public enum LogType {

    FOUND("Found it"), ATTENDED("Attended"), WEBCAM_PHOTO_TAKEN("Webcam Photo Taken"), DNF("Didn't find it"), OTHER(null);
    private final String gpxDescription;
    private static final Map<String, LogType> gpxToType;

    static {
        gpxToType = new HashMap<>();
        for (LogType type : values()) {
            gpxToType.put(type.getGpxDescription(), type);
        }
    }

    LogType(String gpxText) {
        this.gpxDescription = gpxText;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static LogType getByGpxDescription(String description) {
        return gpxToType.containsKey(description) ? gpxToType.get(description) : OTHER;
    }
}
