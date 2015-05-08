/*
 * The MIT License - Copyright (c) 2011-2013 Ville Saalo (http://coord.info/PR32K8V)
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
package zeroone3010.geogpxparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import zeroone3010.geogpxparser.cachelistparsers.CacheListParser;
import zeroone3010.geogpxparser.cachelistparsers.CountryStatsParser;
import zeroone3010.geogpxparser.cachelistparsers.DateStatsParser;
import zeroone3010.geogpxparser.cachelistparsers.OwnerStatsParser;
import zeroone3010.geogpxparser.cachelistparsers.StarChallengeParser;
import zeroone3010.geogpxparser.coordinateformatters.CoordinateFormatter;
import zeroone3010.geogpxparser.coordinateformatters.DefaultCoordinateFormatter;
import zeroone3010.geogpxparser.coordinateformatters.DegreesAndMinutesFormatter;
import zeroone3010.geogpxparser.outputformatters.AbstractTabularDataFormatter;
import zeroone3010.geogpxparser.outputformatters.FormatterFactory;
import zeroone3010.geogpxparser.tabular.TableData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class can be used to parse geocaches from a Groundspeak .gpx file into plain old Java objects (POJO). The cache
 * list can then be processed further and various text files can be created out of it.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class GeoGPXParser {

    private String file = null;

    public static void main(final String[] args) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Usage:");
            System.out.println("1) java [-DcoordinateFormat=(dd|ddmm)] [-Doutput=(xml|html|txt)] -jar GeoGPXParser.jar caches.gpx");
            System.out.println("2) java [-DcoordinateFormat=(dd|ddmm)] [-Doutput=(xml|html|txt)] -jar GeoGPXParser.jar some/directory/with/gpx/files");
            System.out.println("...where \"[...]\" denotes an optional parameter and \"(A|B|C)\" denotes alternatives: either A or B or C.");
            System.exit(1);
        }

        final GeoGPXParser parser = new GeoGPXParser(args[0]);
        final List<Geocache> caches = parser.parse();
        final TableData tabularRepresentation = new CacheListParser(buildCoordinateFormatter()).getTabularInfo(caches);
        final TableData ownerStats = new OwnerStatsParser().getTabularInfo(caches);
        final TableData countryStats = new CountryStatsParser().getTabularInfo(caches);
        final TableData starStats = new StarChallengeParser().getTabularInfo(caches);
        final TableData dateStats = new DateStatsParser().getTabularInfo(caches);

        final String outputType = System.getProperty("output", "html").toLowerCase();

        Stream.of(tabularRepresentation, ownerStats, countryStats, starStats, dateStats)
                .map(td -> FormatterFactory.createFormatter(td, outputType))
                .forEach(GeoGPXParser::writeDataToFile);

        parser.writeHtmlResources();

        info("Done!");
    }

    private static CoordinateFormatter buildCoordinateFormatter() {
        final CoordinateFormatter coordinateFormatter;
        final String coordinateFormat = System.getProperty("coordinateFormat", "ddmm").toLowerCase();
        switch (coordinateFormat) {
            case "dd":
                coordinateFormatter = new DefaultCoordinateFormatter();
                break;
            default:
                coordinateFormatter = new DegreesAndMinutesFormatter();
                break;
        }
        return coordinateFormatter;
    }

    private static void info(final String text) {
        System.out.println(text);
    }

    public GeoGPXParser(final String path) {
        this.file = path;
    }

    public List<Geocache> parse() {
        return parseXmlFilesToObjects(this.file);
    }

    private static <T extends AbstractTabularDataFormatter> void writeDataToFile(final T formatter) {
        final String fileName = formatter.getFileName();
        try {
            info("Writing " + fileName + "...");
            Files.write(Paths.get(fileName), formatter.toString().getBytes(Charset.forName("UTF-8")));
        } catch (IOException ex) {
            System.out.println("Saving the file " + fileName + " failed!");
            ex.printStackTrace();
        }
    }

    private void writeHtmlResources() throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        for (final String filename : new String[]{"jquery-1.9.1.min.js", "jquery.tablesorter.min.js", "jquery-ui.min.js"}) {
            try (final InputStream inputStream = classLoader.getResourceAsStream(filename)) {
                Files.copy(inputStream, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private List<Geocache> parseXMLtoObjects(final Document dom) {
        final List<Geocache> geocaches = new LinkedList<>();
        final Element root = dom.getDocumentElement();

        final NodeList caches = root.getElementsByTagName("wpt");
        info(caches.getLength() + " caches found...");
        if (caches == null || caches.getLength() < 1) {
            return new LinkedList<>();
        }

        for (int i = 0; i < caches.getLength(); i++) {
            final Element wptElement = (Element) caches.item(i);
            final Geocache geocache = getGeocache(wptElement);
            geocaches.add(geocache);
        }
        return geocaches;
    }

    private static Element getSubElement(final Element parent, final String subElementName) {
        return (Element) parent.getElementsByTagName(subElementName).item(0);
    }

    private static String getSubElementContent(final Element parent, final String subElementName) {
        return getSubElement(parent, subElementName).getTextContent();
    }

    private Geocache getGeocache(final Element wptElement) {
        final Geocache cache = new Geocache();

        cache.setLatitude(Double.valueOf(wptElement.getAttribute("lat")));
        cache.setLongitude(Double.valueOf(wptElement.getAttribute("lon")));

        cache.setHidden(parseTime(getSubElementContent(wptElement, "time")));
        cache.setGcCode(getSubElementContent(wptElement, "name"));

        final Element groundspeak = getSubElement(wptElement, "groundspeak:cache");
        cache.setArchived(Boolean.valueOf(groundspeak.getAttribute("archived")));
        cache.setAvailable(Boolean.valueOf(groundspeak.getAttribute("available")));

        cache.setName(getSubElementContent(groundspeak, "groundspeak:name"));
        cache.setCountry(getSubElementContent(groundspeak, "groundspeak:country"));
        cache.setState(getSubElementContent(groundspeak, "groundspeak:state"));
        cache.setOwner(getSubElementContent(groundspeak, "groundspeak:owner"));
        cache.setType(CacheType.getByGpxDescription(getSubElementContent(groundspeak, "groundspeak:type")));
        cache.setSize(CacheSize.getByGpxDescription(getSubElementContent(groundspeak, "groundspeak:container")));
        cache.setDifficulty(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:difficulty")));
        cache.setTerrain(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:terrain")));
        cache.setShortDescription(getSubElementContent(groundspeak, "groundspeak:short_description"));
        cache.setLongDescription(getSubElementContent(groundspeak, "groundspeak:long_description"));
        cache.setHint(getSubElementContent(groundspeak, "groundspeak:encoded_hints"));

        // Parse the attributes into a map where key is the attribute name and
        // value is the value of that attribute:
        final Element attributesElement = getSubElement(groundspeak, "groundspeak:attributes");
        for (final Element attributeElement : new IterableSubElements(attributesElement)) {
            cache.setAttribute(attributeElement.getTextContent(), "1".equals(attributeElement.getAttribute("inc")));
        }

        final Element logsElement = getSubElement(groundspeak, "groundspeak:logs");
        for (final Element logElement : new IterableSubElements(logsElement)) {
            final Log log = new Log();
            log.setId(Long.parseLong(logElement.getAttribute("id")));
            log.setDate(parseTime(getSubElementContent(logElement, "groundspeak:date")));
            log.setType(LogType.getByGpxDescription(getSubElementContent(logElement, "groundspeak:type")));
            log.setUser(getSubElementContent(logElement, "groundspeak:finder"));
            log.setText(getSubElementContent(logElement, "groundspeak:text"));
            cache.addLog(log);
        }

        return cache;
    }

    private List<Geocache> parseXmlFilesToObjects(final String path) {
        final List<Geocache> caches = new LinkedList<>();
        final File[] files;
        final File gpx = new File(path);
        if (gpx.isDirectory()) {
            files = gpx.listFiles((dir, name) -> name.toLowerCase().endsWith(".gpx"));
        } else {
            files = new File[1];
            files[0] = new File(path);
        }
        info("Found " + files.length + " files.");
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        for (File xmlFile : files) {
            info("Parsing file " + xmlFile + "...");
            try {
                final DocumentBuilder db = dbFactory.newDocumentBuilder();
                final Document xml = db.parse(xmlFile);
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

    private LocalDateTime parseTime(final String xmlTimeString) {
        try {
            return ZonedDateTime.parse(xmlTimeString).toLocalDateTime();
        } catch (DateTimeParseException tryFormatWithTimeZoneMissing) {
            if (xmlTimeString.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d")) {
                try {
                    return ZonedDateTime.parse(xmlTimeString + "Z").toLocalDateTime();
                } catch (IllegalArgumentException ignore) {
                }
            }
        }
        return null;
    }
}
