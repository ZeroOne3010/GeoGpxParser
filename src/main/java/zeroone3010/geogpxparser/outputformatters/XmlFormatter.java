package zeroone3010.geogpxparser.outputformatters;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXB;

import zeroone3010.geogpxparser.tabular.TableData;

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
