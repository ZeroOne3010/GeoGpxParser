package zeroone3010.geogpxparser.cachelistparsers;

import org.junit.Test;
import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.ParserTestFixture;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static zeroone3010.geogpxparser.ParserTestFixture.headerRow;
import static zeroone3010.geogpxparser.ParserTestFixture.owner;
import static zeroone3010.geogpxparser.ParserTestFixture.row;
/**
 * Tests the CacheListParser.
 */
public class CacheListParserTest {

    private static CellData gc(final String code) {
        return new CellData(code, "http://coord.info/" + code);
    }

    private static CellData log(final String date, final long logId) {
        return new CellData(date, "http://www.geocaching.com/seek/log.aspx?LID=" + logId);
    }

    @Test
    public void test_getTabularInfo() {
        final List<Geocache> caches = ParserTestFixture.getGeocaches();

        final TableData result = new CacheListParser().getTabularInfo(caches);
        assertNotNull(result);

        final List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(6, rows.size());

        int row = 0;

        assertEquals(headerRow("gccode", "type", "name", "latitude", "longitude", "size", "difficulty", "terrain", "hidden", "owner", "country", "found"), rows.get(row));

        row = 1;
        assertEquals(row(gc("GC111"), "Traditional", "Cache I", "2.200000", "1.100000", "Micro", "1.0", "5.0", "2001-01-02", owner("John"), "Finland", "-"), rows.get(row));

        row = 2;
        assertEquals(row(gc("GC222"), "Multi", "Cache II", "4.400000", "3.300000", "Small", "1.5", "4.5", "2002-03-04", owner("Mike"), "Sweden", log("2010-08-13", 13)), rows.get(row));

        row = 3;
        assertEquals(row(gc("GC333"), "Traditional", "Cache III", "6.600000", "5.500000", "Regular", "2.0", "4.0", "2003-05-06", owner("John"), "Finland", log("2010-08-13", 21)), rows.get(row));

        row = 4;
        assertEquals(row(gc("GC444"), "Letterbox", "Cache IV", "8.800000", "7.700000", "Large", "2.5", "3.5", "2004-07-08", owner("Mike"), "United Kingdom", log("2010-10-13", 34)), rows.get(row));

        row = 5;
        assertEquals(row(gc("GC555"), "Mystery", "Cache V", "10.100000", "9.900000", "Not chosen", "3.0", "3.0", "2005-09-10", owner("Jake"), "Sweden", log("2010-08-13", 13)), rows.get(row));
    }
}
