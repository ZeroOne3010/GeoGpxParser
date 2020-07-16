package io.github.zeroone3010.geogpxparser;

import static java.util.function.Function.identity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        gpxToType = Arrays.asList(CacheType.values()).stream()
                .collect(Collectors.toMap(CacheType::getGpxDescription, identity()));
    }

    private CacheType(String gpxDescriptionParam) {
        this.gpxDescription = gpxDescriptionParam;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static CacheType getByGpxDescription(String description) {
        return Optional.ofNullable(gpxToType.get(description)).orElse(Other);
    }
};