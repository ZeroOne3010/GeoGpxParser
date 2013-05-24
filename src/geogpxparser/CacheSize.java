package geogpxparser;

import java.util.HashMap;
import java.util.Map;

public enum CacheSize {

    Micro("Micro"),
    Small("Small"),
    Regular("Regular"),
    Large("Large"),
    Not_chosen("Not chosen");
    private final String gpxDescription;
    private static final Map<String, CacheSize> gpxToSize;

    static {
        gpxToSize = new HashMap<>();
        for (CacheSize type : values()) {
            gpxToSize.put(type.getGpxDescription(), type);
        }
    }

    private CacheSize(String gpxDescriptionParam) {
        this.gpxDescription = gpxDescriptionParam;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static CacheSize getByGpxDescription(String description) {
        return gpxToSize.containsKey(description) ? gpxToSize.get(description) : Not_chosen;
    }
};