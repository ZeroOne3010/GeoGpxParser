package zeroone3010.geogpxparser.cachelistparsers;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import zeroone3010.geogpxparser.CacheType;
import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Parses cache group statistics from the given list of caches: the number of
 * caches and different cache types each group has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public abstract class AbstractCacheGroupStatsParser implements ICachesToTabularDataParser {

    private Map<String, Group> groups = new LinkedHashMap<>();

    abstract String getTableId();

    abstract String getTableGroupColumnTitle();

    abstract CellData createTableGroupColumnRowContent(Group group);

    abstract String getCacheGroupKey(Geocache cache);

    /**
     * Returns a table with data about cache groups.
     *
     * @param caches A List of Geocache objects
     * @return A table that can be saved into a file in various formats
     */
    @Override
    public final TableData getTabularInfo(List<Geocache> caches) {

        // Parse cache group info into a map:
        for (Geocache cache : caches) {
            addCacheToGroup(getCacheGroupKey(cache), cache);
        }

        TableData result = new TableData(getTableId());

        // Create titles:
        TableRow headerRow = new TableRow(true);
        headerRow.addCell(new CellData(getTableGroupColumnTitle()));
        headerRow.addCell(new CellData("Number of caches"));
        headerRow.addCell(new CellData("Number of cache types"));
        for (CacheType cacheType : CacheType.values()) {
            headerRow.addCell(new CellData(cacheType.name()));
        }
        result.addRow(headerRow);

        // Create data rows:
        for (Group group : groups.values()) {
            TableRow dataRow = new TableRow(false);
            dataRow.addCell(createTableGroupColumnRowContent(group));
            dataRow.addCell(new CellData(String.valueOf(group.getTotalNumberOfCaches())));
            dataRow.addCell(new CellData(String.valueOf(group.getNumberOfCacheTypes())));

            Map<CacheType, Collection<Geocache>> cacheMap = group.getCaches();

            for (Entry<CacheType, Collection<Geocache>> entry : cacheMap.entrySet()) {
                dataRow.addCell(new CellData(String.valueOf(entry.getValue().size())));
            }
            result.addRow(dataRow);
        }

        return result;
    }

    private void addCacheToGroup(String groupName, Geocache cache) {
        if (!groups.containsKey(groupName)) {
            groups.put(groupName, new Group(groupName));
        }

        groups.get(groupName).addCache(cache);
    }

    /**
     * Represents a group of geocaches. Keeps track of the name of the group and
     * the different cache types it has.
     */
    class Group {

        private final String name;
        private Map<CacheType, Collection<Geocache>> caches;

        public Group(String ownerName) {
            name = ownerName;
            caches = new LinkedHashMap<>();
            for (CacheType cacheType : CacheType.values()) {
                caches.put(cacheType, new LinkedHashSet<>());
            }
        }

        public void addCache(Geocache cache) {
            Collection<Geocache> cachesInGroup = caches.get(cache.getType());
            if (cachesInGroup == null) {
                cachesInGroup = new LinkedHashSet<>();
                caches.put(cache.getType(), cachesInGroup);
            }
            cachesInGroup.add(cache);
        }

        public int getTotalNumberOfCaches() {
            return caches.values().stream().mapToInt(Collection::size).reduce(0, Integer::sum);
        }

        public int getNumberOfCacheTypes() {
            return (int) caches.values().stream().filter(val -> val.size() > 0).count();
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public String getName() {
            return name;
        }

        public Map<CacheType, Collection<Geocache>> getCaches() {
            return caches;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && !(o instanceof Group)) {
                return false;
            }

            return name != null && name.equals(((Group) o).getName());
        }
    }
}
