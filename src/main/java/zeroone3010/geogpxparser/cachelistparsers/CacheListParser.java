package zeroone3010.geogpxparser.cachelistparsers;

import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.Log;
import zeroone3010.geogpxparser.LogType;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

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
        headerRow.addCell(new CellData("found"));
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
            if( cache.getHidden() != null) {
                dataRow.addCell(new CellData(OUTPUT_DATE_TIME_FORMAT.print(cache.getHidden())));
            } else {
                dataRow.addCell(new CellData("-"));
            }
            dataRow.addCell(new CellData(cache.getOwner(), Utility.getOwnerUrl(cache.getOwner())));
            final Log log = findFoundLog(cache);
            if (log != null) {
                dataRow.addCell(new CellData(OUTPUT_DATE_TIME_FORMAT.print(log.getDate()), "http://www.geocaching.com/seek/log.aspx?LID=" + log.getId()));
            } else {
                dataRow.addCell(new CellData("-"));
            }
            result.addRow(dataRow);
        }

        return result;
    }

    private Log findFoundLog(final Geocache cache) {
        for (Log log : cache.getLogs()) {
            if (LogType.FOUND.equals(log.getType()) || LogType.ATTENDED.equals(log.getType()) || LogType.WEBCAM_PHOTO_TAKEN.equals(log.getType())) {
                if (log.getDate() != null) {
                    return log;
                }
            }
        }
        return null;
    }
}
