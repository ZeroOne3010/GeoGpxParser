package zeroone3010.geogpxparser.cachelistparsers;

import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Utility {
    private static final String BASE_OWNER_SEARCH_URL = "http://www.geocaching.com/seek/nearest.aspx?u=";
    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Utility() { /* prevent */}

    static String getOwnerUrl(final String owner) {
        String otherCachesByOwnerUrl;
        try {
            otherCachesByOwnerUrl = BASE_OWNER_SEARCH_URL + URLEncoder.encode(owner, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            otherCachesByOwnerUrl = null;
        }
        return otherCachesByOwnerUrl;
    }
    
    static Log findFoundLog(final Geocache cache) {
        return cache.getLogs().stream() //
                .filter(log -> log.getType().countsAsFind() && log.getDate() != null) //
                .findFirst().orElse(null);
    }

    static String formatDate(final TemporalAccessor date) {
        return OUTPUT_DATE_TIME_FORMAT.format(date);
    }
}