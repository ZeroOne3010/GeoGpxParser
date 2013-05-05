package geogpxparser.cachelistparsers;

import geogpxparser.CacheType;
import geogpxparser.Geocache;
import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Parses owner statistics from the given list of caches: the number of caches
 * and different cache types each owner has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class OwnerStatsParser implements ICachesToTabularDataParser {

    private Map<String, Owner> owners = new LinkedHashMap<>();

    /**
     * Returns a String of the tabular format with data about cache owners.
     *
     * @param caches A List of Geocache objects
     * @return A String that can be saved into a file and opened in a
     * spreadsheet program.
     */
    @Override
    public TableData getTabularInfo(List<Geocache> caches) {

        // Parse cache owner info into a map:
        for (Geocache cache : caches) {
            addCacheForOwner(cache.getOwner(), cache.getType());
        }

        TableData result = new TableData("owners");

        // Create titles:
        TableRow headerRow = new TableRow(true);
        headerRow.addCell(new CellData("Owner"));
        headerRow.addCell(new CellData("Number of caches"));
        headerRow.addCell(new CellData("Number of cache types"));
        for (CacheType cacheType : CacheType.values()) {
            headerRow.addCell(new CellData(cacheType.name()));
        }
        result.addRow(headerRow);

        // Create data rows:
        for (Owner owner : owners.values()) {
            TableRow dataRow = new TableRow(false);
            dataRow.addCell(new CellData(owner.getName(), Utility.getOwnerUrl(owner.getName())));
            dataRow.addCell(new CellData(String.valueOf(owner.getTotalNumberOfCaches())));
            dataRow.addCell(new CellData(String.valueOf(owner.getNumberOfCacheTypes())));

            Map<CacheType, Integer> cacheMap = owner.getCaches();

            for (Entry<CacheType, Integer> entry : cacheMap.entrySet()) {
                dataRow.addCell(new CellData(String.valueOf(entry.getValue())));
            }
            result.addRow(dataRow);
        }

        return result;
    }

    /**
     * Initializes a new owner in the map if required, then adds +1 to the
     * amount of the caches of the given type.
     *
     * @param owner Name of the cache owner
     * @param cacheType Type of the cache
     */
    private void addCacheForOwner(String ownerName, CacheType cacheType) {
        if (!owners.containsKey(ownerName)) {
            owners.put(ownerName, new Owner(ownerName));
        }

        owners.get(ownerName).addCache(cacheType);
    }

    /**
     * Represents someone who owns some geocaches. Keeps track of the name of
     * the owner and the amount of different cache types they have.
     */
    private class Owner {

        private final String name;
        private Map<CacheType, Integer> caches;

        public Owner(String ownerName) {
            name = ownerName;
            caches = new LinkedHashMap<>();
            for (CacheType cacheType : CacheType.values()) {
                caches.put(cacheType, 0);
            }
        }

        public void addCache(CacheType cacheType) {
            int amount = caches.get(cacheType);
            amount++;
            caches.put(cacheType, amount);
        }

        public int getTotalNumberOfCaches() {
            int result = 0;
            for (Entry<CacheType, Integer> cacheEntry : caches.entrySet()) {
                result += cacheEntry.getValue();
            }
            return result;
        }

        public int getNumberOfCacheTypes() {
            int result = 0;
            for (Entry<CacheType, Integer> cacheEntry : caches.entrySet()) {
                if (cacheEntry.getValue() > 0) {
                    result++;
                }
            }
            return result;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        public String getName() {
            return name;
        }

        public Map<CacheType, Integer> getCaches() {
            return caches;
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && !(o instanceof Owner)) {
                return false;
            }

            return name != null && name.equals(((Owner) o).getName());
        }
    }
}
