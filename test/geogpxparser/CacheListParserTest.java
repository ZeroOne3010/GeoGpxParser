package geogpxparser;

import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the CacheListParser.
 */
public class CacheListParserTest extends AbstractParserTest {

    @Test
    public void test_getTabularInfo() {
        TableData result = new CacheListParser().getTabularInfo(caches);
        assertNotNull(result);

        List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(6, rows.size());

        int row = 0;
        assertTrue(rows.get(0).isHeader());
        int col = 0;

        assertEquals("gccode", rows.get(row).getCells().get(col++).getText());
        assertEquals("type", rows.get(row).getCells().get(col++).getText());
        assertEquals("name", rows.get(row).getCells().get(col++).getText());
        assertEquals("longitude", rows.get(row).getCells().get(col++).getText());
        assertEquals("latitude", rows.get(row).getCells().get(col++).getText());
        assertEquals("size", rows.get(row).getCells().get(col++).getText());
        assertEquals("difficulty", rows.get(row).getCells().get(col++).getText());
        assertEquals("terrain", rows.get(row).getCells().get(col++).getText());
        assertEquals("hidden", rows.get(row).getCells().get(col++).getText());
        assertEquals("owner", rows.get(row).getCells().get(col++).getText());

        row = 1;
        col = 0;
        assertEquals("GC111", rows.get(row).getCells().get(col++).getText());
        assertEquals("Traditional", rows.get(row).getCells().get(col++).getText());
        assertEquals("Cache I", rows.get(row).getCells().get(col++).getText());
        assertEquals("1.1", rows.get(row).getCells().get(col++).getText());
        assertEquals("2.2", rows.get(row).getCells().get(col++).getText());
        assertEquals("Micro", rows.get(row).getCells().get(col++).getText());
        assertEquals("1.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("5.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("2001-01-02", rows.get(row).getCells().get(col++).getText());
        assertEquals("John", rows.get(row).getCells().get(col++).getText());

        row = 2;
        col = 0;
        assertEquals("GC222", rows.get(row).getCells().get(col++).getText());
        assertEquals("Multi", rows.get(row).getCells().get(col++).getText());
        assertEquals("Cache II", rows.get(row).getCells().get(col++).getText());
        assertEquals("3.3", rows.get(row).getCells().get(col++).getText());
        assertEquals("4.4", rows.get(row).getCells().get(col++).getText());
        assertEquals("Small", rows.get(row).getCells().get(col++).getText());
        assertEquals("1.5", rows.get(row).getCells().get(col++).getText());
        assertEquals("4.5", rows.get(row).getCells().get(col++).getText());
        assertEquals("2002-03-04", rows.get(row).getCells().get(col++).getText());
        assertEquals("Mike", rows.get(row).getCells().get(col++).getText());

        row = 3;
        col = 0;
        assertEquals("GC333", rows.get(row).getCells().get(col++).getText());
        assertEquals("Traditional", rows.get(row).getCells().get(col++).getText());
        assertEquals("Cache III", rows.get(row).getCells().get(col++).getText());
        assertEquals("5.5", rows.get(row).getCells().get(col++).getText());
        assertEquals("6.6", rows.get(row).getCells().get(col++).getText());
        assertEquals("Regular", rows.get(row).getCells().get(col++).getText());
        assertEquals("2.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("4.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("2003-05-06", rows.get(row).getCells().get(col++).getText());
        assertEquals("John", rows.get(row).getCells().get(col++).getText());

        row = 4;
        col = 0;
        assertEquals("GC444", rows.get(row).getCells().get(col++).getText());
        assertEquals("Letterbox", rows.get(row).getCells().get(col++).getText());
        assertEquals("Cache IV", rows.get(row).getCells().get(col++).getText());
        assertEquals("7.7", rows.get(row).getCells().get(col++).getText());
        assertEquals("8.8", rows.get(row).getCells().get(col++).getText());
        assertEquals("Large", rows.get(row).getCells().get(col++).getText());
        assertEquals("2.5", rows.get(row).getCells().get(col++).getText());
        assertEquals("3.5", rows.get(row).getCells().get(col++).getText());
        assertEquals("2004-07-08", rows.get(row).getCells().get(col++).getText());
        assertEquals("Mike", rows.get(row).getCells().get(col++).getText());

        row = 5;
        col = 0;
        assertEquals("GC555", rows.get(row).getCells().get(col++).getText());
        assertEquals("Mystery", rows.get(row).getCells().get(col++).getText());
        assertEquals("Cache V", rows.get(row).getCells().get(col++).getText());
        assertEquals("9.9", rows.get(row).getCells().get(col++).getText());
        assertEquals("10.1", rows.get(row).getCells().get(col++).getText());
        assertEquals("Not_chosen", rows.get(row).getCells().get(col++).getText());
        assertEquals("3.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("3.0", rows.get(row).getCells().get(col++).getText());
        assertEquals("2005-09-10", rows.get(row).getCells().get(col++).getText());
        assertEquals("Jake", rows.get(row).getCells().get(col++).getText());
    }
}
