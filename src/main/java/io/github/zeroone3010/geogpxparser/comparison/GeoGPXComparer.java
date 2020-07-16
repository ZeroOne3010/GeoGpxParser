package io.github.zeroone3010.geogpxparser.comparison;

import io.github.zeroone3010.geogpxparser.GeoXMLReader;
import io.github.zeroone3010.geogpxparser.tabular.CellData;
import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public final class GeoGPXComparer {

    private final List<Geocache> cacheList1;
    private final List<Geocache> cacheList2;

    public GeoGPXComparer(final String file1, final String file2) {
        this(new GeoXMLReader(file1).parse(), new GeoXMLReader(file2).parse());
    }

    public GeoGPXComparer(final List<Geocache> cacheList1, final List<Geocache> cacheList2) {
        this.cacheList1 = cacheList1;
        this.cacheList2 = cacheList2;
    }

    public TableData compare() {
        final Set<SimpleLogData> caches1 = parse(cacheList1);
        final Set<SimpleLogData> caches2 = parse(cacheList2);

        final Set<SimpleLogData> only1 = new HashSet<>(caches1);
        only1.removeIf(o -> caches2.contains(o));

        final Set<SimpleLogData> only2 = new HashSet<>(caches2);
        only2.removeIf(o -> caches1.contains(o));

        return createComparisonTable(only1, only2);
    }

    private Set<SimpleLogData> parse(final List<Geocache> caches) {
        return caches.stream().map(cache -> new SimpleLogData(cache)).collect(toSet());
    }

    private TableData createComparisonTable(final Set<SimpleLogData> data1, final Set<SimpleLogData> data2) {

        final Set<String> interestingDates = new TreeSet<>();
        interestingDates.addAll(data1.stream().map(SimpleLogData::getFoundDate).collect(toSet()));
        interestingDates.addAll(data2.stream().map(SimpleLogData::getFoundDate).collect(toSet()));

        final Map<String, List<SimpleLogData>> d1 = data1.stream().collect(groupingBy(SimpleLogData::getFoundDate));
        final Map<String, List<SimpleLogData>> d2 = data2.stream().collect(groupingBy(SimpleLogData::getFoundDate));

        final TableData result = new TableData("comparison");
        final TableRow headerRow = new TableRow(true);
        headerRow.addCell(new CellData("Date"));
        headerRow.addCell(new CellData("1"));
        headerRow.addCell(new CellData("2"));
        result.addRow(headerRow);

        interestingDates.stream().map(date -> {
            final TableRow row = new TableRow(false);
            row.addCell(new CellData(date));
            row.addCell(new CellData(d1.getOrDefault(date, emptyList()).stream()
                    .map(x -> x.toString()).collect(Collectors.joining(",\n"))));

            row.addCell(new CellData(d2.getOrDefault(date, emptyList()).stream()
                    .map(x -> x.toString()).collect(Collectors.joining(",\n"))));
            return row;
        }).forEachOrdered(row -> result.addRow(row));

        return result;
    }


}
