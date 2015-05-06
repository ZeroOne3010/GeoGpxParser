package zeroone3010.geogpxparser.cachelistparsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import zeroone3010.geogpxparser.AbstractParserTest;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Tests the CacheListParser.
 */
public class StarChallengeParserTest extends AbstractParserTest {
    @Test
    public void test_getTabularInfo() {
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
