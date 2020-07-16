package io.github.zeroone3010.geogpxparser.cachelistparsers;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.tabular.CellData;

/**
 * Parses country statistics from the given list of caches: the number of caches
 * and different cache types each country has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class CountryStatsParser extends AbstractCacheGroupStatsParser {
    @Override
    String getTableId() {
        return "countries";
    }

    @Override
    String getTableGroupColumnTitle() {
        return "Country";
    }

    @Override
    CellData createTableGroupColumnRowContent(final Group group) {
        return new CellData(group.getName());
    }

    @Override
    String getCacheGroupKey(final Geocache cache) {
        return cache.getCountry();
    }
}
