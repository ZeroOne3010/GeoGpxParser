package io.github.zeroone3010.geogpxparser.outputformatters;

import io.github.zeroone3010.geogpxparser.tabular.TableData;

import javax.xml.bind.JAXB;
import java.io.ByteArrayOutputStream;

/**
 * Formats the given TableData object into an XML string.
 */
class XmlFormatter extends AbstractTabularDataFormatter {

    private static final String FILE_EXTENSION = "xml";

    public XmlFormatter(final TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JAXB.marshal(getTable(), outputStream);
        return outputStream.toString();
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
