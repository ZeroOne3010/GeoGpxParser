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
    
    private CellData(CellBuilder builder) {
        this.text = builder.text;
        this.url = builder.url;
    }
    
    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }
    
    /** A builder to avoid telescopic constructors. **/
    public static class CellBuilder {
        private String text = "";
        private URL url = null;
        
        public CellBuilder text(String text) {
            this.text = text;
            return this;
        }
        
        public CellBuilder url(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(CellData.class.getName()).log(Level.SEVERE, null, ex);
            }
            return this;
        }
        
        public CellData build() {
            return new CellData(this);
        }
    }
}
