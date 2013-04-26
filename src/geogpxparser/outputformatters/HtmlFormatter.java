package geogpxparser.outputformatters;

import geogpxparser.tabular.TableData;
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

/**
 * Formats the given TableData object into an HTML string.
 */
public class HtmlFormatter extends AbstractTabularDataFormatter {

    public HtmlFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(getTable().getClass());
            Transformer transformer = TransformerFactory.newInstance().newTransformer(findXslt());
            JAXBSource xmlInput = new JAXBSource(context, getTable());
            ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
            transformer.transform(xmlInput, new StreamResult(htmlOutputStream));
            return htmlOutputStream.toString("UTF-8");
        } catch (JAXBException | TransformerException | UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Source findXslt() {
        Source xslt;
        File xslFile = new File("xmlToHtml.xsl");
        if (xslFile.canRead()) {
            // Found an external XSL file:
            xslt = new StreamSource(xslFile);
        } else {
            // Default to an internal XSL file inside the application .jar file:
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("geogpxparser/outputformatters/xmlToHtml.xsl");
            xslt = new StreamSource(inputStream);
        }
        return xslt;
    }
}
