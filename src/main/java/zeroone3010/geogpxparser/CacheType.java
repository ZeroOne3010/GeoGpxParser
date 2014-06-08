package zeroone3010.geogpxparser;

import java.util.HashMap;
import java.util.Map;

public enum CacheType {

    Traditional("Traditional Cache"),
    Multi("Multi-cache"),
    Mystery("Unknown Cache"),
    Letterbox("Letterbox Hybrid"),
    Event("Event Cache"),
    EarthCache("Earthcache"),
    Virtual("Virtual Cache"),
    Webcam("Webcam Cache"),
    Wherigo("Wherigo Cache"),
    MegaEvent("Mega-Event Cache"),
    CITO("Cache In Trash Out Event"),
    Other(null);
    private final String gpxDescription;
    private static final Map<String, CacheType> gpxToType;

    static {
        gpxToType = new HashMap<>();
        for (CacheType type : values()) {
            gpxToType.put(type.getGpxDescription(), type);
        }
    }

    private CacheType(String gpxDescriptionParam) {
        this.gpxDescription = gpxDescriptionParam;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static CacheType getByGpxDescription(String description) {
        return gpxToType.containsKey(description) ? gpxToType.get(description) : Other;
    }
};