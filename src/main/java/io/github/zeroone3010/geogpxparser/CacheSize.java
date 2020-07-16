package io.github.zeroone3010.geogpxparser;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public enum CacheSize {

    Micro("Micro"),
    Small("Small"),
    Regular("Regular"),
    Large("Large"),
    Virtual("Virtual"),
    Other("Other"),
    Not_chosen("Not chosen");
    private final String gpxDescription;
    private static final Map<String, CacheSize> gpxToSize;

    static {
        gpxToSize = Arrays.asList(CacheSize.values()).stream()
                .collect(Collectors.toMap(CacheSize::getGpxDescription, identity()));
    }

    private CacheSize(String gpxDescriptionParam) {
        this.gpxDescription = gpxDescriptionParam;
    }

    public String getGpxDescription() {
        return this.gpxDescription;
    }

    public static CacheSize getByGpxDescription(String description) {
        return Optional.ofNullable(gpxToSize.get(description)).orElse(Not_chosen);
    }
};
