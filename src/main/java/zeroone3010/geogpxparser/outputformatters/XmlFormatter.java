package zeroone3010.geogpxparser.outputformatters;

import zeroone3010.geogpxparser.tabular.TableData;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXB;

/**
 * Formats the given TableData object into an XML string.
 */
public class XmlFormatter extends AbstractTabularDataFormatter {

    private static final String FILE_EXTENSION = "xml";

    public XmlFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(getTable(), outputStream);
        return outputStream.toString();
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
