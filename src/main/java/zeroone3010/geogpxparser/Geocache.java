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
package zeroone3010.geogpxparser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents one geocache.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class Geocache {

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

    public String getGcCode() {
        return gcCode;
    }

    public void setGcCode(String gcCode) {
        this.gcCode = gcCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLatitudeString() {
        return getCoordinateInDegreesAndMinutes('N', 'S', latitude);
    }

    public String getLongitudeString() {
        return getCoordinateInDegreesAndMinutes('E', 'W', longitude);
    }

    private static String getCoordinateInDegreesAndMinutes(char posLetter, char negLetter, double value) {
        final int degrees = Math.abs((int) value);
        final double minutes = (Math.abs(value) - degrees) * 60d;
        final char letter = value >= 0 ? posLetter : negLetter;
        return String.format(Locale.US, "%c %d° %5.3f'", letter, degrees, minutes);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getHidden() {
        return hidden;
    }

    public void setHidden(LocalDateTime published) {
        this.hidden = published;
    }

    public CacheType getType() {
        return type;
    }

    public void setType(CacheType type) {
        this.type = type;
    }

    public CacheSize getSize() {
        return size;
    }

    public void setSize(CacheSize size) {
        this.size = size;
    }

    public float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(float newRating) {
        this.difficulty = newRating;
    }

    public float getTerrain() {
        return terrain;
    }

    public void setTerrain(float newRating) {
        this.terrain = newRating;
    }

    public Map<String, Boolean> getAttributes() {
        return attributes;
    }

    public void setAttribute(String attribute, Boolean value) {
        this.attributes.put(attribute, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getHint(boolean decrypted) {
        if (decrypted) {
            return hint;
        }
        StringBuilder encryptedHint = new StringBuilder();
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

    /**
     * Sets the hint for this cache. The hint should be set in plain text!
     *
     * @param hint The hint to be set.
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void addLog(Log log) {
        this.logs.add(log);
    }
}
