package geogpxparser;

import geogpxparser.tabular.CellData;
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

        int row = 0;
        assertEquals(row(true, "Owner", "Number of caches", "Number of cache types", "Traditional", "Multi", "Mystery",
                "Letterbox", "Event", "EarthCache", "Virtual", "Webcam", "Wherigo", "MegaEvent", "CITO", "Other"),
                rows.get(0));

        row = 1;
        assertEquals(row("John", "2", "1", "2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 2;
        assertEquals(row("Mike", "2", "2", "0", "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 3;
        assertEquals(row("Jake", "1", "1", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));
    }
}
