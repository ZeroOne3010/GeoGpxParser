package io.github.zeroone3010.geogpxparser.cachelistparsers;

import io.github.zeroone3010.geogpxparser.ParserTestFixture;
import org.junit.Test;
import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static io.github.zeroone3010.geogpxparser.ParserTestFixture.row;
/**
 * Tests for the OwnerStatsParser class.
 */
public class OwnerStatsParserTest {

    @Test
    public void test_getTabularInfo() {
        final List<Geocache> caches = ParserTestFixture.getGeocaches();

        // Assert that the test data has been initialized correctly:
        assertNotNull(caches);
        assertEquals(5, caches.size());

        // Start testing:
        final TableData result = new OwnerStatsParser().getTabularInfo(caches);
        assertNotNull(result);

        final List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(4, rows.size());

        int row = 0;
        assertEquals(ParserTestFixture.headerRow("Owner", "Number of caches", "Number of cache types", "Traditional", "Multi", "Mystery",
                "Letterbox", "Event", "EarthCache", "Virtual", "Webcam", "Wherigo", "MegaEvent", "CITO", "Other"),
                rows.get(0));

        row = 1;
        assertEquals(row(ParserTestFixture.owner("John"), "2", "1", "2", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 2;
        assertEquals(row(ParserTestFixture.owner("Mike"), "2", "2", "0", "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 3;
        assertEquals(row(ParserTestFixture.owner("Jake"), "1", "1", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));
    }
}
