/*
 * The MIT License - Copyright (c) 2011-2013 Ville Saalo (http://coord.info/PR32K8V)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package io.github.zeroone3010.geogpxparser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents one geocache.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public final class Geocache {
    private final String gcCode;
    private final double latitude;
    private final double longitude;
    private final String country;
    private final String state;
    private final LocalDateTime hidden;
    private final CacheType type;
    private final CacheSize size;
    private final float difficulty;
    private final float terrain;
    private final Map<String, Boolean> attributes;
    private final String name;
    private final String owner;
    private final String shortDescription;
    private final String longDescription;
    private final String hint;
    private final boolean available;
    private final boolean archived;
    private final List<Log> logs;

    private Geocache(String gcCode, double latitude, double longitude, String country, String state,
                     LocalDateTime hidden, CacheType type, CacheSize size, float difficulty, float terrain,
                     Map<String, Boolean> attributes, String name, String owner, String shortDescription,
                     String longDescription, String hint, boolean available, boolean archived, List<Log> logs) {
        this.gcCode = gcCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.state = state;
        this.hidden = hidden;
        this.type = type;
        this.size = size;
        this.difficulty = difficulty;
        this.terrain = terrain;
        this.attributes = Collections.unmodifiableMap(attributes);
        this.name = name;
        this.owner = owner;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.hint = hint;
        this.available = available;
        this.archived = archived;
        this.logs = Collections.unmodifiableList(logs);
    }

    public String getGcCode() {
        return gcCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public LocalDateTime getHidden() {
        return hidden;
    }

    public CacheType getType() {
        return type;
    }

    public CacheSize getSize() {
        return size;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public float getTerrain() {
        return terrain;
    }

    public Map<String, Boolean> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getHint(final boolean decrypted) {
        if (decrypted) {
            return hint;
        }
        final StringBuilder encryptedHint = new StringBuilder();
        for (char character : hint.toCharArray()) {
            if (character >= 'A' && character <= 'Z') {
                encryptedHint.append((char) ((((character + 13) - 'A') % 26) + 'A'));
            } else if (character >= 'a' && character <= 'z') {
                encryptedHint.append((char) ((((character + 13) - 'a') % 26) + 'a'));
            } else {
                encryptedHint.append(character);
            }
        }
        return encryptedHint.toString();
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isArchived() {
        return archived;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String gcCode;
        private double latitude;
        private double longitude;
        private String country;
        private String state;
        private LocalDateTime hidden;
        private CacheType type;
        private CacheSize size;
        private float difficulty;
        private float terrain;
        private Map<String, Boolean> attributes = new HashMap<>();
        private String name;
        private String owner;
        private String shortDescription;
        private String longDescription;
        private String hint;
        private boolean available;
        private boolean archived;
        private final List<Log> logs = new ArrayList<>();

        public Builder gcCode(String gcCode) {
            this.gcCode = gcCode;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder hidden(LocalDateTime published) {
            this.hidden = published;
            return this;
        }

        public Builder type(CacheType type) {
            this.type = type;
            return this;
        }

        public Builder size(CacheSize size) {
            this.size = size;
            return this;
        }

        public Builder difficulty(float newRating) {
            this.difficulty = newRating;
            return this;
        }

        public Builder terrain(float newRating) {
            this.terrain = newRating;
            return this;
        }

        public Builder attribute(String attribute, Boolean value) {
            this.attributes.put(attribute, value);
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder longDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        /**
         * Sets the hint for this cache. The hint should be set in plain text!
         *
         * @param hint The hint to be set.
         */
        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder available(boolean available) {
            this.available = available;
            return this;
        }

        public Builder archived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder addLog(Log log) {
            this.logs.add(log);
            return this;
        }

        public Geocache build() {
            return new Geocache(gcCode, latitude, longitude, country, state,
                    hidden, type, size, difficulty, terrain, attributes, name,
                    owner, shortDescription, longDescription, hint, available,
                    archived, logs);
        }
    }
}
