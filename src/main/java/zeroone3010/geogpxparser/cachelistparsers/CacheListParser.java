package zeroone3010.geogpxparser.cachelistparsers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.Log;
import zeroone3010.geogpxparser.coordinateformatters.CoordinateFormatter;
import zeroone3010.geogpxparser.coordinateformatters.DefaultCoordinateFormatter;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * A parser for transforming a List of Geocache objects into table format.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class CacheListParser implements ICachesToTabularDataParser {

    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final CoordinateFormatter coordinateFormatter;

    public CacheListParser() {
        this(new DefaultCoordinateFormatter());
    }

    public CacheListParser(final CoordinateFormatter coordinateFormatter) {
        this.coordinateFormatter = coordinateFormatter;
    }

    @Override
    public TableData getTabularInfo(final List<Geocache> caches) {

        final TableData result = new TableData("caches");
        final TableRow headerRow = new TableRow(true);

        headerRow.addCell(new CellData("gccode"));
        headerRow.addCell(new CellData("type"));
        headerRow.addCell(new CellData("name"));
        headerRow.addCell(new CellData("longitude"));
        headerRow.addCell(new CellData("latitude"));
        headerRow.addCell(new CellData("size"));
        headerRow.addCell(new CellData("difficulty"));
        headerRow.addCell(new CellData("terrain"));
        headerRow.addCell(new CellData("hidden"));
        headerRow.addCell(new CellData("owner"));
        headerRow.addCell(new CellData("country"));
        headerRow.addCell(new CellData("found"));
        result.addRow(headerRow);

        for (final Geocache cache : caches) {
            final TableRow dataRow = new TableRow(false);
            dataRow.addCell(new CellData(cache.getGcCode(), "http://coord.info/" + cache.getGcCode()));
            dataRow.addCell(new CellData(cache.getType().name()));
            dataRow.addCell(new CellData(cache.getName()));
            dataRow.addCell(new CellData(coordinateFormatter.formatLongitude(cache.getLongitude())));
            dataRow.addCell(new CellData(coordinateFormatter.formatLatitude(cache.getLatitude())));
            dataRow.addCell(new CellData(cache.getSize().getGpxDescription()));
            dataRow.addCell(new CellData(String.valueOf(cache.getDifficulty())));
            dataRow.addCell(new CellData(String.valueOf(cache.getTerrain())));
            if( cache.getHidden() != null) {
                dataRow.addCell(new CellData(OUTPUT_DATE_TIME_FORMAT.format(cache.getHidden())));
            } else {
                dataRow.addCell(new CellData("-"));
            }
            dataRow.addCell(new CellData(cache.getOwner(), Utility.getOwnerUrl(cache.getOwner())));
            dataRow.addCell(new CellData(cache.getCountry()));
            final Log log = findFoundLog(cache);
            if (log != null) {
                dataRow.addCell(new CellData(OUTPUT_DATE_TIME_FORMAT.format(log.getDate()), "http://www.geocaching.com/seek/log.aspx?LID=" + log.getId()));
            } else {
                dataRow.addCell(new CellData("-"));
            }
            result.addRow(dataRow);
        }

        return result;
    }

    private Log findFoundLog(final Geocache cache) {
        return cache.getLogs().stream() //
                .filter(log -> log.getType().countsAsFind() && log.getDate() != null) //
                .findFirst().orElse(null);
    }
}
