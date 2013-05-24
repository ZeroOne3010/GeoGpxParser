package geogpxparser.cachelistparsers;

import geogpxparser.Geocache;
import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.List;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A parser for transforming a List of Geocache objects into table format.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class CacheListParser implements ICachesToTabularDataParser {

    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public TableData getTabularInfo(final List<Geocache> caches) {

        TableData result = new TableData("caches");
        TableRow headerRow = new TableRow(true);

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
        result.addRow(headerRow);

        for (Geocache cache : caches) {
            TableRow dataRow = new TableRow(false);
            dataRow.addCell(new CellData(cache.getGcCode(), "http://coord.info/" + cache.getGcCode()));
            dataRow.addCell(new CellData(cache.getType().name()));
            dataRow.addCell(new CellData(cache.getName()));
            dataRow.addCell(new CellData(String.valueOf(cache.getLongitude())));
            dataRow.addCell(new CellData(String.valueOf(cache.getLatitude())));
            dataRow.addCell(new CellData(cache.getSize().getGpxDescription()));
            dataRow.addCell(new CellData(String.valueOf(cache.getDifficulty())));
            dataRow.addCell(new CellData(String.valueOf(cache.getTerrain())));
            dataRow.addCell(new CellData(OUTPUT_DATE_TIME_FORMAT.print(cache.getHidden())));
            dataRow.addCell(new CellData(cache.getOwner(), Utility.getOwnerUrl(cache.getOwner())));
            result.addRow(dataRow);
        }

        return result;
    }
}
