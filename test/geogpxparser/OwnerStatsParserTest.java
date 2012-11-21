package geogpxparser;

import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the OwnerStatsParser class.
 */
public class OwnerStatsParserTest extends AbstractParserTest {
    
    @Test
    public void test_getTabularInfo() {
        // Assert that the test data has been initialized correctly:
        assertNotNull(caches);
        assertEquals(5, caches.size());
        
        // Start testing:
        TableData result = new OwnerStatsParser().getTabularInfo(caches);
        assertNotNull(result);
        
        List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(4, rows.size());
        
        // Check the headers:
        int row = 0;
        assertTrue(rows.get(0).isHeader());
        int col = 0;
        assertEquals("Owner", rows.get(row).getCells().get(col++).getText());
        assertEquals("Number of caches", rows.get(row).getCells().get(col++).getText());
        assertEquals("Number of cache types", rows.get(row).getCells().get(col++).getText());
        assertEquals("Traditional", rows.get(row).getCells().get(col++).getText());
        assertEquals("Multi", rows.get(row).getCells().get(col++).getText());
        assertEquals("Mystery", rows.get(row).getCells().get(col++).getText());
        assertEquals("Letterbox", rows.get(row).getCells().get(col++).getText());
        assertEquals("Event", rows.get(row).getCells().get(col++).getText());
        assertEquals("EarthCache", rows.get(row).getCells().get(col++).getText());
        assertEquals("Virtual", rows.get(row).getCells().get(col++).getText());
        assertEquals("Webcam", rows.get(row).getCells().get(col++).getText());
        assertEquals("Wherigo", rows.get(row).getCells().get(col++).getText());
        assertEquals("MegaEvent", rows.get(row).getCells().get(col++).getText());
        assertEquals("CITO", rows.get(row).getCells().get(col++).getText());
        assertEquals("Other", rows.get(row).getCells().get(col++).getText());
        
        // Check the data rows:
        
        row = 1;
        col = 0;
        assertEquals("John", rows.get(row).getCells().get(col++).getText());
        assertEquals("2", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("2", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        
        row = 2;
        col = 0;
        assertEquals("Mike", rows.get(row).getCells().get(col++).getText());
        assertEquals("2", rows.get(row).getCells().get(col++).getText());
        assertEquals("2", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());

        row = 3;
        col = 0;
        assertEquals("Jake", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("1", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
        assertEquals("0", rows.get(row).getCells().get(col++).getText());
    }
}
