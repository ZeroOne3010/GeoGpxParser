package io.github.zeroone3010.geogpxparser.cachelistparsers;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.ParserTestFixture;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;
import org.junit.Test;

import java.util.List;

import static io.github.zeroone3010.geogpxparser.ParserTestFixture.headerRow;
import static io.github.zeroone3010.geogpxparser.ParserTestFixture.row;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests the CacheListParser.
 */
public class StarChallengeParserTest {
    @Test
    public void test_getTabularInfo() {
        final List<Geocache> caches = ParserTestFixture.getGeocaches();

        final TableData result = new StarChallengeParser().getTabularInfo(caches);
        assertNotNull(result);

        final List<TableRow> rows = result.getRows();
        assertNotNull(rows);
        assertEquals(3, rows.size());

        int row = 0;

        assertEquals(headerRow("date", "caches", "Σ D", "Σ T", "Σ (D + T)"), rows.get(row));

        row = 1;
        assertEquals(row("2010-08-13", "3", "6.5", "11.5", "18.0"), rows.get(row));

        row = 2;
        assertEquals(row("2010-10-13", "1", "2.5", "3.5", "6.0"), rows.get(row));
    }
}
