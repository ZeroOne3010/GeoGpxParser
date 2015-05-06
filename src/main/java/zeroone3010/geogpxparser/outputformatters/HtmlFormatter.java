package zeroone3010.geogpxparser.outputformatters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import zeroone3010.geogpxparser.tabular.TableData;

/**
 * Formats the given TableData object into an HTML string.
 */
class HtmlFormatter extends AbstractTabularDataFormatter {

    private static final String XSLT_FILE_NAME = "xmlToHtml.xsl";
    private static final String FILE_EXTENSION = "html";

    public HtmlFormatter(final TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        try {
            final JAXBContext context = JAXBContext.newInstance(getTable().getClass());
            final Transformer transformer = TransformerFactory.newInstance().newTransformer(findXslt());
            final JAXBSource xmlInput = new JAXBSource(context, getTable());
            final ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
            transformer.transform(xmlInput, new StreamResult(htmlOutputStream));
            return htmlOutputStream.toString("UTF-8");
        } catch (JAXBException | TransformerException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Source findXslt() {
        final Source xslt;
        final File xslFile = new File(XSLT_FILE_NAME);
        if (xslFile.canRead()) {
            // Found an external XSL file:
            xslt = new StreamSource(xslFile);
        } else {
            // Default to an internal XSL file inside the application .jar file:
            final ClassLoader classLoader = getClass().getClassLoader();
            final InputStream inputStream = classLoader.getResourceAsStream("xmlToHtml.xsl");
            xslt = new StreamSource(inputStream);
        }
        return xslt;
    }

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
