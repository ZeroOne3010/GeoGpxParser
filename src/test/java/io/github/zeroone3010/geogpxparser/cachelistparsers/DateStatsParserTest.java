package io.github.zeroone3010.geogpxparser.cachelistparsers;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.ParserTestFixture;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        Assert.assertEquals(ParserTestFixture.headerRow("Date", "Number of caches", "Number of cache types", "Traditional", "Multi", "Mystery",
                "Letterbox", "Event", "EarthCache", "Virtual", "Webcam", "Wherigo", "MegaEvent", "CITO", "Other"),
                rows.get(0));

        row = 1;
        Assert.assertEquals(ParserTestFixture.row("2010-08-13", "3", "3", "1", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));

        row = 2;
        Assert.assertEquals(ParserTestFixture.row("2010-10-13", "1", "1", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0"), rows.get(row));
    }
}
