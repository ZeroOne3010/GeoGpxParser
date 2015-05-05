package zeroone3010.geogpxparser.cachelistparsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import zeroone3010.geogpxparser.AbstractParserTest;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Tests the CacheListParser.
 */
public class CacheListParserTest extends AbstractParserTest {

    private static CellData gc(final String code) {
        return new CellData(code, "http://coord.info/" + code);
    }

    private static CellData log(final String date, final long logId) {
        return new CellData(date, "http://www.geocaching.com/seek/log.aspx?LID=" + logId);
    }

    @Test
    public void test_getTabularInfo() {
        final TableData result = new CacheListParser().getTabularInfo(caches);
        assertNotNull(result);

        final List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(6, rows.size());

        int row = 0;

        assertEquals(headerRow("gccode", "type", "name", "longitude", "latitude", "size", "difficulty", "terrain", "hidden", "owner", "country", "found"), rows.get(row));

        row = 1;
        assertEquals(row(gc("GC111"), "Traditional", "Cache I", "1.100000", "2.200000", "Micro", "1.0", "5.0", "2001-01-02", owner("John"), "Finland", "-"), rows.get(row));

        row = 2;
        assertEquals(row(gc("GC222"), "Multi", "Cache II", "3.300000", "4.400000", "Small", "1.5", "4.5", "2002-03-04", owner("Mike"), "Sweden", log("2010-08-13", 13)), rows.get(row));

        row = 3;
        assertEquals(row(gc("GC333"), "Traditional", "Cache III", "5.500000", "6.600000", "Regular", "2.0", "4.0", "2003-05-06", owner("John"), "Finland", log("2010-09-13", 21)), rows.get(row));

        row = 4;
        assertEquals(row(gc("GC444"), "Letterbox", "Cache IV", "7.700000", "8.800000", "Large", "2.5", "3.5", "2004-07-08", owner("Mike"), "United Kingdom", log("2010-10-13", 34)), rows.get(row));

        row = 5;
        assertEquals(row(gc("GC555"), "Mystery", "Cache V", "9.900000", "10.100000", "Not chosen", "3.0", "3.0", "2005-09-10", owner("Jake"), "Sweden", log("2010-08-13", 13)), rows.get(row));
    }
}
