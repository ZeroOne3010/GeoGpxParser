package zeroone3010.geogpxparser;

import org.junit.Ignore;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides test data for subclasses.
 */
@Ignore
public abstract class AbstractParserTest {

    protected List<Geocache> caches;

    private static TableRow row(final boolean header, final Object... cellValues) {
        final TableRow row = new TableRow(header);
        for (final Object cellValue : cellValues) {
            if (cellValue instanceof String) {
                row.addCell(new CellData((String) cellValue));
            } else if (cellValue instanceof CellData) {
                row.addCell((CellData) cellValue);
            } else {
                throw new UnsupportedOperationException("Only String and CellData objects are accepted.");
            }
        }
        return row;
    }

    public static TableRow row(final Object... cellValues) {
        return row(false, cellValues);
    }

    public static TableRow headerRow(final Object... cellValues) {
        return row(true, cellValues);
    }

    protected static CellData owner(final String owner) {
        return new CellData(owner, "http://www.geocaching.com/seek/nearest.aspx?u=" + owner);
    }

    public AbstractParserTest() {
        caches = new ArrayList<>();

        final Log dnfLog1 = Log.builder().date(LocalDateTime.of(2010, 6, 13, 15, 00)).text(":(").type(LogType.DNF).user("a").build();
        final Log dnfLog2 = Log.builder().date(LocalDateTime.of(2010, 7, 13, 15, 00)).text(":((").type(LogType.DNF).user("a").build();
        final Log foundLog = Log.builder().date(LocalDateTime.of(2010, 8, 13, 15, 00)).text(":)").type(LogType.FOUND).user("a").id(13).build();
        final Log attendedLog = Log.builder().date(LocalDateTime.of(2010, 8, 13, 15, 00)).text(":)").type(LogType.ATTENDED).user("a").id(21).build();
        final Log webcamLog = Log.builder().date(LocalDateTime.of(2010, 10, 13, 15, 00)).text(":)").type(LogType.WEBCAM_PHOTO_TAKEN).user("a").id(34).build();


        final Geocache cache1 = Geocache.builder()
                .gcCode("GC111")
                .name("Cache I")
                .owner("John")
                .type(CacheType.Traditional)
                .longitude(1.1)
                .latitude(2.2)
                .size(CacheSize.Micro)
                .difficulty(1)
                .terrain(5)
                .hidden(LocalDateTime.of(2001, 1, 2, 00, 00))
                .addLog(dnfLog1)
                .country("Finland")
                .build();

        final Geocache cache2 = Geocache.builder()
                .gcCode("GC222")
                .name("Cache II")
                .owner("Mike")
                .type(CacheType.Multi)
                .longitude(3.3)
                .latitude(4.4)
                .size(CacheSize.Small)
                .difficulty(1.5f)
                .terrain(4.5f)
                .hidden(LocalDateTime.of(2002, 3, 4, 01, 00))
                .addLog(dnfLog2)
                .addLog(foundLog)
                .country("Sweden")
                .build();

        final Geocache cache3 = Geocache.builder()
                .gcCode("GC333")
                .name("Cache III")
                .owner("John")
                .type(CacheType.Traditional)
                .longitude(5.5)
                .latitude(6.6)
                .size(CacheSize.Regular)
                .difficulty(2)
                .terrain(4)
                .hidden(LocalDateTime.of(2003, 5, 6, 02, 00))
                .addLog(attendedLog)
                .country("Finland")
                .build();

        final Geocache cache4 = Geocache.builder()
                .gcCode("GC444")
                .name("Cache IV")
                .owner("Mike")
                .type(CacheType.Letterbox)
                .longitude(7.7)
                .latitude(8.8)
                .size(CacheSize.Large)
                .difficulty(2.5f)
                .terrain(3.5f)
                .hidden(LocalDateTime.of(2004, 7, 8, 03, 00))
                .addLog(webcamLog)
                .country("United Kingdom")
                .build();

        final Geocache cache5 = Geocache.builder()
                .gcCode("GC555")
                .name("Cache V")
                .owner("Jake")
                .type(CacheType.Mystery)
                .longitude(9.9)
                .latitude(10.10)
                .size(CacheSize.Not_chosen)
                .difficulty(3)
                .terrain(3)
                .hidden(LocalDateTime.of(2005, 9, 10, 06, 07))
                .addLog(foundLog)
                .addLog(dnfLog2)
                .country("Sweden")
                .build();

        caches.add(cache1);
        caches.add(cache2);
        caches.add(cache3);
        caches.add(cache4);
        caches.add(cache5);
    }
}
