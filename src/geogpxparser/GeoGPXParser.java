/*
 * The MIT License - Copyright (c) 2011-2012 Ville Saalo (http://coord.info/PR32K8V)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package geogpxparser;

import geogpxparser.cachelistparsers.CacheListParser;
import geogpxparser.cachelistparsers.OwnerStatsParser;
import geogpxparser.outputformatters.HtmlFormatter;
import geogpxparser.outputformatters.TabSeparatedValuesFormatter;
import geogpxparser.outputformatters.XmlFormatter;
import geogpxparser.tabular.TableData;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class can be used to parse geocaches from a Groundspeak .gpx file into plain old Java objects (POJO). The cache
 * list can then be processed further and various text files can be created out of it.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class GeoGPXParser {

    private String file = null;
    private final DateTimeFormatter XML_DATE_TIME_FORMAT = ISODateTimeFormat.dateTimeNoMillis();

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Usage:");
            System.out.println("1) java [-Doutput=(xml|html|txt)] -jar GeoGPXParser.jar caches.gpx");
            System.out.println("2) java [-Doutput=(xml|html|txt)] -jar GeoGPXParser.jar some/directory/with/gpx/files");
            System.out.println("...where \"[...]\" denotes an optional parameter and \"(A|B|C)\" denotes alternatives: either A or B or C.");
            System.exit(1);
        }

        GeoGPXParser parser = new GeoGPXParser(args[0]);
        List<Geocache> caches = parser.parse();
        TableData tabularRepresentation = new CacheListParser().getTabularInfo(caches);
        TableData ownerStats = new OwnerStatsParser().getTabularInfo(caches);

        String outputType = System.getProperty("output", "txt").toLowerCase();
        String cachesOutput;
        String ownersOutput;

        switch (outputType) {
            case "xml":
                cachesOutput = new XmlFormatter(tabularRepresentation).toString();
                ownersOutput = new XmlFormatter(ownerStats).toString();
                break;
            case "html":
                cachesOutput = new HtmlFormatter(tabularRepresentation).toString();
                ownersOutput = new HtmlFormatter(ownerStats).toString();
                parser.writeHtmlResources();
                break;
            default:
                outputType = "txt";
                cachesOutput = new TabSeparatedValuesFormatter(tabularRepresentation).toString();
                ownersOutput = new TabSeparatedValuesFormatter(ownerStats).toString();
                break;
        }

        info("Writing the caches into a file...");
        writeFile("caches." + outputType, cachesOutput);

        info("Writing owner stats into a file...");
        writeFile("owners." + outputType, ownersOutput);

        info("Done!");
    }

    private static void info(String text) {
        System.out.println(text);
    }

    public GeoGPXParser(String path) {
        this.file = path;
    }

    public List<Geocache> parse() {
        return parseXmlFilesToObjects(this.file);
    }

    private static void writeFile(final String fileName, final String contents) {
        try {
            Files.write(Paths.get(fileName), contents.getBytes());
        } catch (IOException ex) {
            System.out.println("Saving the file " + fileName + " failed!");
            ex.printStackTrace();
        }
    }

    private void writeHtmlResources() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        for (String filename : new String[]{"jquery-1.9.1.min.js", "jquery.tablesorter.min.js"}) {
            InputStream inputStream = classLoader.getResourceAsStream("geogpxparser/outputformatters/resources/" + filename);
            Files.copy(inputStream, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private List<Geocache> parseXMLtoObjects(Document dom) {
        List<Geocache> geocaches = new LinkedList<>();
        Element root = dom.getDocumentElement();

        NodeList caches = root.getElementsByTagName("wpt");
        info(caches.getLength() + " caches found...");
        if (caches == null || caches.getLength() < 1) {
            return new LinkedList<>();
        }

        for (int i = 0; i < caches.getLength(); i++) {
            Element wptElement = (Element) caches.item(i);
            Geocache geocache = getGeocache(wptElement);
            geocaches.add(geocache);
        }
        return geocaches;
    }

    private static Element getSubElement(Element parent, String subElementName) {
        return (Element) parent.getElementsByTagName(subElementName).item(0);
    }

    private static String getSubElementContent(Element parent, String subElementName) {
        return getSubElement(parent, subElementName).getTextContent();
    }

    private Geocache getGeocache(Element wptElement) {
        Geocache cache = new Geocache();

        cache.setLatitude(Double.valueOf(wptElement.getAttribute("lat")));
        cache.setLongitude(Double.valueOf(wptElement.getAttribute("lon")));

        DateTime time = XML_DATE_TIME_FORMAT.parseDateTime(getSubElementContent(wptElement, "time"));
        cache.setHidden(time);
        cache.setGcCode(getSubElementContent(wptElement, "name"));

        Element groundspeak = getSubElement(wptElement, "groundspeak:cache");
        cache.setArchived(Boolean.valueOf(groundspeak.getAttribute("archived")));
        cache.setAvailable(Boolean.valueOf(groundspeak.getAttribute("available")));

        cache.setName(getSubElementContent(groundspeak, "groundspeak:name"));
        cache.setCountry(getSubElementContent(groundspeak, "groundspeak:country"));
        cache.setState(getSubElementContent(groundspeak, "groundspeak:state"));
        cache.setName(getSubElementContent(groundspeak, "groundspeak:name"));
        cache.setState(getSubElementContent(groundspeak, "groundspeak:state"));
        cache.setOwner(getSubElementContent(groundspeak, "groundspeak:owner"));
        cache.setType(CacheType.getByGpxDescription(getSubElementContent(groundspeak, "groundspeak:type")));
        try {
            cache.setSize(Geocache.CacheSize.valueOf(getSubElementContent(groundspeak, "groundspeak:container")));
        } catch (IllegalArgumentException ex) {
            cache.setSize(Geocache.CacheSize.Not_chosen);
        }
        cache.setDifficulty(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:difficulty")));
        cache.setTerrain(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:terrain")));
        cache.setShortDescription(getSubElementContent(groundspeak, "groundspeak:short_description"));
        cache.setLongDescription(getSubElementContent(groundspeak, "groundspeak:long_description"));
        cache.setHint(getSubElementContent(groundspeak, "groundspeak:encoded_hints"));

        // Parse the attributes into a map where key is the attribute name and
        // value is the value of that attribute:
        Element attributesElement = getSubElement(groundspeak, "groundspeak:attributes");
        if (attributesElement != null) {
            Node attributeNode = attributesElement.getFirstChild();
            while (attributeNode != null) {
                if (attributeNode != null && attributeNode instanceof Element) {
                    Element attributeElement = (Element) attributeNode;
                    cache.setAttribute(attributeElement.getTextContent(), "1".equals(attributeElement.getAttribute("inc")));
                }
                attributeNode = attributeNode.getNextSibling();
            }
        }

        return cache;
    }

    private List<Geocache> parseXmlFilesToObjects(String path) {
        List<Geocache> caches = new LinkedList<>();
        File[] files;
        File gpx = new File(path);
        if (gpx.isDirectory()) {
            files = gpx.listFiles(new GpxFileFilter());
        } else {
            files = new File[1];
            files[0] = new File(path);
        }
        info("Found " + files.length + " files.");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        for (File xmlFile : files) {
            info("Parsing file " + xmlFile + "...");
            try {
                DocumentBuilder db = dbFactory.newDocumentBuilder();
                Document xml = db.parse(xmlFile);
                caches.addAll(this.parseXMLtoObjects(xml));
            } catch (ParserConfigurationException | SAXException xmlException) {
                System.err.println("Error in parsing XML!");
                xmlException.printStackTrace();
            } catch (IllegalArgumentException | IOException ioException) {
                System.err.println("Error in reading file '" + xmlFile + "'!");
                ioException.printStackTrace();
            }
        }
        return caches;
    }

    private static final class GpxFileFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".gpx");
        }
    }
}
