package io.github.zeroone3010.geogpxparser.comparison;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.Log;
import io.github.zeroone3010.geogpxparser.LogType;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static io.github.zeroone3010.geogpxparser.ParserTestFixture.headerRow;
import static io.github.zeroone3010.geogpxparser.ParserTestFixture.row;
import static junit.framework.Assert.assertEquals;

public class GeoGPXComparerTest {

    GeoGPXComparer comparer;

    @Before
    public void loadCaches() {

        final Geocache only1 = Geocache.builder()
                .gcCode("GC111")
                .name("Cache I")
                .addLog(Log.builder().date(LocalDateTime.of(2010, 8, 13, 15, 00)).type(LogType.FOUND).build())
                .build();

        final Geocache only2_1 = Geocache.builder()
                .gcCode("GC222")
                .name("Cache II")
                .addLog(Log.builder().date(LocalDateTime.of(2010, 8, 13, 15, 00)).type(LogType.FOUND).build())
                .build();

        final Geocache only2_2 = Geocache.builder()
                .gcCode("GC333")
                .name("Cache III")
                .addLog(Log.builder().date(LocalDateTime.of(2010, 8, 14, 15, 00)).type(LogType.FOUND).build())
                .build();

        final Geocache only2_3 = Geocache.builder()
                .gcCode("GC444")
                .name("Cache IV")
                .addLog(Log.builder().date(LocalDateTime.of(2010, 8, 14, 15, 00)).type(LogType.FOUND).build())
                .build();

        final Geocache bothButOnDifferentDays1 = Geocache.builder()
                .gcCode("GC555")
                .name("Cache V")
                .addLog(Log.builder().date(LocalDateTime.of(2014, 8, 13, 15, 00)).type(LogType.FOUND).build())
                .build();

        final Geocache bothButOnDifferentDays2 = Geocache.builder()
                .gcCode("GC555")
                .name("Cache V")
                .addLog(Log.builder().date(LocalDateTime.of(2013, 5, 2, 15, 00)).type(LogType.FOUND).build())
                .build();

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
