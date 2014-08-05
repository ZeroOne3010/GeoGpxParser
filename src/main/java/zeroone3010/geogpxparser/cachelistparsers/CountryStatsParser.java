package zeroone3010.geogpxparser.cachelistparsers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import zeroone3010.geogpxparser.CacheType;
import zeroone3010.geogpxparser.Geocache;
import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Parses country statistics from the given list of caches: the number of caches
 * and different cache types each country has.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class CountryStatsParser implements ICachesToTabularDataParser {

    private Map<String, Country> countries = new LinkedHashMap<>();

    /**
     * Returns a String of the tabular format with data about cache countries.
     *
     * @param caches A List of Geocache objects
     * @return A String that can be saved into a file and opened in a
     * spreadsheet program.
     */
    @Override
    public TableData getTabularInfo(List<Geocache> caches) {

        // Parse cache country info into a map:
        for (Geocache cache : caches) {
            addCacheForCountry(cache.getCountry(), cache.getType());
        }

        TableData result = new TableData("countries");

        // Create titles:
        TableRow headerRow = new TableRow(true);
        headerRow.addCell(new CellData("Country"));
        headerRow.addCell(new CellData("Number of caches"));
        headerRow.addCell(new CellData("Number of cache types"));
        for (CacheType cacheType : CacheType.values()) {
            headerRow.addCell(new CellData(cacheType.name()));
        }
        result.addRow(headerRow);

        // Create data rows:
        for (Country country : countries.values()) {
            TableRow dataRow = new TableRow(false);
            dataRow.addCell(new CellData(country.getName()));
            dataRow.addCell(new CellData(String.valueOf(country.getTotalNumberOfCaches())));
            dataRow.addCell(new CellData(String.valueOf(country.getNumberOfCacheTypes())));

            Map<CacheType, Integer> cacheMap = country.getCaches();

            for (Entry<CacheType, Integer> entry : cacheMap.entrySet()) {
                dataRow.addCell(new CellData(String.valueOf(entry.getValue())));
            }
            result.addRow(dataRow);
        }

        return result;
    }

    /**
     * Initializes a new country in the map if required, then adds +1 to the
     * amount of the caches of the given type.
     *
     * @param owner Name of the cache country
     * @param cacheType Type of the cache
     */
    private void addCacheForCountry(String countryName, CacheType cacheType) {
        if (!countries.containsKey(countryName)) {
            countries.put(countryName, new Country(countryName));
        }

        countries.get(countryName).addCache(cacheType);
    }

    /**
     * Represents some country with some geocaches. Keeps track of the name of
     * the country and the amount of different cache types it has.
     */
    private class Country {

        private final String name;
        private Map<CacheType, Integer> caches;

        public Country(String countryName) {
            name = countryName;
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
            return caches.values().stream().reduce(0, Integer::sum);
        }

        public int getNumberOfCacheTypes() {
            return (int) caches.values().stream().filter(val -> val > 0).count();
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
            if (o != null && !(o instanceof Country)) {
                return false;
            }

            return name != null && name.equals(((Country) o).getName());
        }
    }
}
