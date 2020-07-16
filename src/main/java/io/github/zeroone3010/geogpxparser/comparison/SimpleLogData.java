package io.github.zeroone3010.geogpxparser.comparison;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.Utility;

public class SimpleLogData {
    private final String gcCode;
    private final String name;
    private final String foundDate;

    public SimpleLogData(final Geocache cache) {
        this.gcCode = cache.getGcCode();
        this.name = cache.getName();
        this.foundDate = Utility.formatDate(Utility.findFoundLog(cache).getDate());
    }

    public String getFoundDate() {
        return foundDate;
    }

    public String getGcCode() {
        return gcCode;
    }

    @Override
    public String toString() {
        return String.format("%s %s", gcCode, name);
    }

    @Override
    public boolean equals(final Object o) {
        return gcCode.equals(((SimpleLogData) o).getGcCode());
    }

    @Override
    public int hashCode() {
        return gcCode.hashCode();
    }
}