package geogpxparser.cachelistparsers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

class Utility {
    private static final String BASE_OWNER_SEARCH_URL = "http://www.geocaching.com/seek/nearest.aspx?u=";

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
}
