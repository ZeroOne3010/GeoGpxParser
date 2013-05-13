package geogpxparser.cachelistparsers;

import geogpxparser.AbstractParserTest;
import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the CacheListParser.
 */
public class CacheListParserTest extends AbstractParserTest {

    private static CellData gc(String code) {
        return new CellData(code, "http://coord.info/" + code);
    }

    @Test
    public void test_getTabularInfo() {
        TableData result = new CacheListParser().getTabularInfo(caches);
        assertNotNull(result);

        List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(6, rows.size());

        int row = 0;

        assertEquals(headerRow("gccode", "type", "name", "longitude", "latitude", "size", "difficulty", "terrain", "hidden", "owner"), rows.get(row));

        row = 1;
        assertEquals(row(gc("GC111"), "Traditional", "Cache I", "1.1", "2.2", "Micro", "1.0", "5.0", "2001-01-02", owner("John")), rows.get(row));

        row = 2;
        assertEquals(row(gc("GC222"), "Multi", "Cache II", "3.3", "4.4", "Small", "1.5", "4.5", "2002-03-04", owner("Mike")), rows.get(row));

        row = 3;
        assertEquals(row(gc("GC333"), "Traditional", "Cache III", "5.5", "6.6", "Regular", "2.0", "4.0", "2003-05-06", owner("John")), rows.get(row));

        row = 4;
        assertEquals(row(gc("GC444"), "Letterbox", "Cache IV", "7.7", "8.8", "Large", "2.5", "3.5", "2004-07-08", owner("Mike")), rows.get(row));

        row = 5;
        assertEquals(row(gc("GC555"), "Mystery", "Cache V", "9.9", "10.1", "Not_chosen", "3.0", "3.0", "2005-09-10", owner("Jake")), rows.get(row));
    }
}
