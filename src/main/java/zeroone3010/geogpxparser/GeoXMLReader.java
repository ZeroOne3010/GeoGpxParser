/*
 * The MIT License - Copyright (c) 2011-2015 Ville Saalo (http://coord.info/PR32K8V)
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

public final class GeoXMLReader {

    private String file = null;

    public GeoXMLReader(final String path) {
        this.file = path;
    }

    private static void info(final String text) {
        System.out.println(text);
    }

    public List<Geocache> parse() {
        return parseXmlFilesToObjects(this.file);
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
        final Element groundspeak = getSubElement(wptElement, "groundspeak:cache");
        final Geocache.Builder builder = Geocache.builder()

                .latitude(Double.valueOf(wptElement.getAttribute("lat")))
                .longitude(Double.valueOf(wptElement.getAttribute("lon")))

                .hidden(parseTime(getSubElementContent(wptElement, "time")))
                .gcCode(getSubElementContent(wptElement, "name"))

                .archived(Boolean.valueOf(groundspeak.getAttribute("archived")))
                .available(Boolean.valueOf(groundspeak.getAttribute("available")))

                .name(getSubElementContent(groundspeak, "groundspeak:name"))
                .country(getSubElementContent(groundspeak, "groundspeak:country"))
                .state(getSubElementContent(groundspeak, "groundspeak:state"))
                .owner(getSubElementContent(groundspeak, "groundspeak:owner"))
                .type(CacheType.getByGpxDescription(getSubElementContent(groundspeak, "groundspeak:type")))
                .size(CacheSize.getByGpxDescription(getSubElementContent(groundspeak, "groundspeak:container")))
                .difficulty(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:difficulty")))
                .terrain(Float.parseFloat(getSubElementContent(groundspeak, "groundspeak:terrain")))
                .shortDescription(getSubElementContent(groundspeak, "groundspeak:short_description"))
                .longDescription(getSubElementContent(groundspeak, "groundspeak:long_description"))
                .hint(getSubElementContent(groundspeak, "groundspeak:encoded_hints"));

        // Parse the attributes into a map where key is the attribute name and
        // value is the value of that attribute:
        final Element attributesElement = getSubElement(groundspeak, "groundspeak:attributes");
        for (final Element attributeElement : new IterableSubElements(attributesElement)) {
            builder.attribute(attributeElement.getTextContent(), "1".equals(attributeElement.getAttribute("inc")));
        }

        final Element logsElement = getSubElement(groundspeak, "groundspeak:logs");
        for (final Element logElement : new IterableSubElements(logsElement)) {
            final Log log = Log.builder()
                    .id(Long.parseLong(logElement.getAttribute("id")))
                    .date(parseTime(getSubElementContent(logElement, "groundspeak:date")))
                    .type(LogType.getByGpxDescription(getSubElementContent(logElement, "groundspeak:type")))
                    .user(getSubElementContent(logElement, "groundspeak:finder"))
                    .text(getSubElementContent(logElement, "groundspeak:text"))
                    .build();
            builder.addLog(log);
        }

        return builder.build();
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
