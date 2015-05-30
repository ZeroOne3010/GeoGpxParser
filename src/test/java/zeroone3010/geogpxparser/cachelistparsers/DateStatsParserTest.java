package zeroone3010.geogpxparser.cachelistparsers;

import org.junit.Test;
import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.ParserTestFixture;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static zeroone3010.geogpxparser.ParserTestFixture.headerRow;
import static zeroone3010.geogpxparser.ParserTestFixture.row;

public class DateStatsParserTest {

    @Test
    public void test_getTabularInfo() {
        final List<Geocache> caches = ParserTestFixture.getGeocaches();

        // Assert that the test data has been initialized correctly:
        assertNotNull(caches);
        assertEquals(5, caches.size());

        // Start testing:
        final TableData result = new DateStatsParser().getTabularInfo(caches);
        assertNotNull(result);

        final List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(3, rows.size());

        int row = 0;
        assertEquals(headerRow("Date", "Number of caches", "Number of cache types", "Traditional", "Multi", "Mystery",
                        "Letterbox", "Event", "EarthCache", "Virtual", "Webcam", "Wherigo", "MegaEvent", "CITO", "Other"),
                rows.get(0));

        row = 1;
        assertEquals(row("2010-08-13", "3", "3", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 2;
        assertEquals(row("2010-10-13", "1", "1", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));
    }
}
