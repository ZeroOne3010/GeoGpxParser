package geogpxparser.tabular;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that represents data in a table cell.
 */
public class CellData {
    /** The text that's displayed in the cell. **/
    private final String text;
    
    /** The URL to which the text should link to, if any. **/
    private final URL url;

    public CellData(String text) {
        this.text = text;
        this.url = null;
    }

    public CellData(String text, String url) {
        this.text = text;
        URL tempUrl = null;
        try {
            tempUrl = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CellData.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.url = tempUrl;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }
}
