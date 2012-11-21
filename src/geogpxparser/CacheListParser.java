package geogpxparser;

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
public class CacheListParser implements ICachesToTextParser {

    private static final DateTimeFormatter OUTPUT_DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final String separator = "\t";

    @Override
    public TableData getTabularInfo(final List<Geocache> caches) {

        TableData result = new TableData();
        TableRow headerRow = new TableRow(true);
        
        headerRow.addCell(new CellData.CellBuilder().text("gccode").build());
        headerRow.addCell(new CellData.CellBuilder().text("type").build());
        headerRow.addCell(new CellData.CellBuilder().text("name").build());
        headerRow.addCell(new CellData.CellBuilder().text("longitude").build());
        headerRow.addCell(new CellData.CellBuilder().text("latitude").build());
        headerRow.addCell(new CellData.CellBuilder().text("size").build());
        headerRow.addCell(new CellData.CellBuilder().text("difficulty").build());
        headerRow.addCell(new CellData.CellBuilder().text("terrain").build());
        headerRow.addCell(new CellData.CellBuilder().text("published").build());
        headerRow.addCell(new CellData.CellBuilder().text("owner").build());
        result.addRow(headerRow);
        
        for (Geocache cache : caches) {
            TableRow dataRow = new TableRow(false);
            dataRow.addCell(new CellData.CellBuilder().text(cache.getGcCode())
                            .url("http://coord.info/" + cache.getGcCode()).build());
            dataRow.addCell(new CellData.CellBuilder().text(cache.getType().name()).build());
            dataRow.addCell(new CellData.CellBuilder().text(cache.getName()).build());
            dataRow.addCell(new CellData.CellBuilder().text(String.valueOf(cache.getLongitude())).build());
            dataRow.addCell(new CellData.CellBuilder().text(String.valueOf(cache.getLatitude())).build());
            dataRow.addCell(new CellData.CellBuilder().text(String.valueOf(cache.getSize())).build());
            dataRow.addCell(new CellData.CellBuilder().text(String.valueOf(cache.getDifficulty())).build());
            dataRow.addCell(new CellData.CellBuilder().text(String.valueOf(cache.getTerrain())).build());
            dataRow.addCell(new CellData.CellBuilder().text(OUTPUT_DATE_TIME_FORMAT.print(cache.getPublished())).build());
            dataRow.addCell(new CellData.CellBuilder().text(cache.getOwner()).build());
            result.addRow(dataRow);
        }

        return result;
    }
}
