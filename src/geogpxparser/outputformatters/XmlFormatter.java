package geogpxparser.outputformatters;

import geogpxparser.tabular.TableData;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXB;

/**
 * Formats the given TableData object into a plain text file.
 */
public class XmlFormatter extends AbstractTabularDataFormatter {

    public XmlFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(getTable(), outputStream);
        return outputStream.toString();
    }
}
