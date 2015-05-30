package zeroone3010.geogpxparser.comparison;

import org.junit.Before;
import org.junit.Test;
import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.Log;
import zeroone3010.geogpxparser.LogType;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static zeroone3010.geogpxparser.AbstractParserTest.headerRow;
import static zeroone3010.geogpxparser.AbstractParserTest.row;

public class GeoGPXComparerTest {

    GeoGPXComparer comparer;

    @Before
    public void loadCaches() {

        final Geocache only1 = new Geocache();
        only1.setGcCode("GC111");
        only1.setName("Cache I");
        only1.addLog(new Log().setDate(LocalDateTime.of(2010, 8, 13, 15, 00)).setType(LogType.FOUND));

        final Geocache only2_1 = new Geocache();
        only2_1.setGcCode("GC222");
        only2_1.setName("Cache II");
        only2_1.addLog(new Log().setDate(LocalDateTime.of(2010, 8, 13, 15, 00)).setType(LogType.FOUND));

        final Geocache only2_2 = new Geocache();
        only2_2.setGcCode("GC333");
        only2_2.setName("Cache III");
        only2_2.addLog(new Log().setDate(LocalDateTime.of(2010, 8, 14, 15, 00)).setType(LogType.FOUND));

        final Geocache only2_3 = new Geocache();
        only2_3.setGcCode("GC444");
        only2_3.setName("Cache IV");
        only2_3.addLog(new Log().setDate(LocalDateTime.of(2010, 8, 14, 15, 00)).setType(LogType.FOUND));

        final Geocache bothButOnDifferentDays1 = new Geocache();
        bothButOnDifferentDays1.setGcCode("GC555");
        bothButOnDifferentDays1.setName("Cache V");
        bothButOnDifferentDays1.addLog(new Log().setDate(LocalDateTime.of(2014, 8, 13, 15, 00)).setType(LogType.FOUND));

        final Geocache bothButOnDifferentDays2 = new Geocache();
        bothButOnDifferentDays2.setGcCode("GC555");
        bothButOnDifferentDays2.setName("Cache V");
        bothButOnDifferentDays2.addLog(new Log().setDate(LocalDateTime.of(2013, 5, 2, 15, 00)).setType(LogType.FOUND));

        comparer = new GeoGPXComparer(Arrays.asList(only1, bothButOnDifferentDays1),
                Arrays.asList(only2_1, only2_2, bothButOnDifferentDays2, only2_3));
    }

    @Test
    public void should_create_correct_table() {
        final TableData tableData = comparer.compare();

        final List<TableRow> rows = tableData.getRows();

        assertEquals(3, rows.size());
        int row = 0;
        assertEquals(headerRow("Date", "1", "2"), rows.get(row++));
        assertEquals(row("2010-08-13", "GC111 Cache I", "GC222 Cache II"), rows.get(row++));
        assertEquals(row("2010-08-14", "", "GC333 Cache III,\nGC444 Cache IV"), rows.get(row++));

        System.out.println(tableData);
    }
}
