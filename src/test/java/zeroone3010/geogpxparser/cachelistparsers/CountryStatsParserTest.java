package zeroone3010.geogpxparser.cachelistparsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import zeroone3010.geogpxparser.AbstractParserTest;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Tests for the CountryStatsParser class.
 */
public class CountryStatsParserTest extends AbstractParserTest {

    @Test
    public void test_getTabularInfo() {
        // Assert that the test data has been initialized correctly:
        assertNotNull(caches);
        assertEquals(5, caches.size());

        // Start testing:
        TableData result = new CountryStatsParser().getTabularInfo(caches);
        assertNotNull(result);

        List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(4, rows.size());

        int row = 0;
        assertEquals(headerRow("Country", "Number of caches", "Number of cache types", "Traditional", "Multi", "Mystery",
                "Letterbox", "Event", "EarthCache", "Virtual", "Webcam", "Wherigo", "MegaEvent", "CITO", "Other"),
                rows.get(0));

        row = 1;
        assertEquals(row("Finland", "2", "1", "2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 2;
        assertEquals(row("Sweden", "2", "2", "0", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 3;
        assertEquals(row("United Kingdom", "1", "1", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));
    }
}
