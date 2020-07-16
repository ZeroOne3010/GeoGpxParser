package io.github.zeroone3010.geogpxparser.cachelistparsers;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.Log;
import io.github.zeroone3010.geogpxparser.Utility;
import io.github.zeroone3010.geogpxparser.tabular.CellData;

import java.util.Optional;

/**
 * Parses date statistics from the given list of caches: the number of finds
 * and different cache types each date has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class DateStatsParser extends AbstractCacheGroupStatsParser {
    @Override
    String getTableId() {
        return "dates";
    }

    @Override
    String getTableGroupColumnTitle() {
        return "Date";
    }

    @Override
    CellData createTableGroupColumnRowContent(final Group group) {
        return new CellData(group.getName());
    }

    @Override
    String getCacheGroupKey(final Geocache cache) {
        return Optional.ofNullable(Utility.findFoundLog(cache))
                .map(Log::getDate)
                .map(Utility::formatDate)
                .orElse(null);
    }
}
