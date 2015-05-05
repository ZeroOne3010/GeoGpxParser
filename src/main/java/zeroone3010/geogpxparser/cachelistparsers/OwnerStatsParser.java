package zeroone3010.geogpxparser.cachelistparsers;

import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.tabular.CellData;

/**
 * Parses owner statistics from the given list of caches: the number of caches
 * and different cache types each owner has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class OwnerStatsParser extends AbstractCacheGroupStatsParser {
    @Override
    String getTableId() {
        return "owners";
    }

    @Override
    String getTableGroupColumnTitle() {
        return "Owner";
    }

    @Override
    CellData createTableGroupColumnRowContent(final Group group) {
        return new CellData(group.getName(), Utility.getOwnerUrl(group.getName()));
    }

    @Override
    String getCacheGroupKey(final Geocache cache) {
        return cache.getOwner();
    }
}
