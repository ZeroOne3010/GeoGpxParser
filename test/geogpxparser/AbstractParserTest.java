package geogpxparser;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Provides test data for subclasses.
 */
public abstract class AbstractParserTest {
    protected List<Geocache> caches;

    public AbstractParserTest() {
        caches = new ArrayList<>();
        Geocache cache1 = new Geocache();
        cache1.setGcCode("GC111");
        cache1.setName("Cache I");
        cache1.setOwner("John");
        cache1.setType(Geocache.CacheType.Traditional);
        cache1.setLongitude(1.1);
        cache1.setLatitude(2.2);
        cache1.setSize(Geocache.CacheSize.Micro);
        cache1.setDifficulty(1);
        cache1.setTerrain(5);
        cache1.setPublished(new DateTime(2001, 1, 2, 00, 00));
        
        Geocache cache2 = new Geocache();
        cache2.setGcCode("GC222");
        cache2.setName("Cache II");
        cache2.setOwner("Mike");
        cache2.setType(Geocache.CacheType.Multi);
        cache2.setLongitude(3.3);
        cache2.setLatitude(4.4);
        cache2.setSize(Geocache.CacheSize.Small);
        cache2.setDifficulty(1.5f);
        cache2.setTerrain(4.5f);
        cache2.setPublished(new DateTime(2002, 3, 4, 01, 00));
        
        Geocache cache3 = new Geocache();
        cache3.setGcCode("GC333");
        cache3.setName("Cache III");
        cache3.setOwner("John");
        cache3.setType(Geocache.CacheType.Traditional);
        cache3.setLongitude(5.5);
        cache3.setLatitude(6.6);
        cache3.setSize(Geocache.CacheSize.Regular);
        cache3.setDifficulty(2);
        cache3.setTerrain(4);
        cache3.setPublished(new DateTime(2003, 5, 6, 02, 00));
        
        Geocache cache4 = new Geocache();
        cache4.setGcCode("GC444");
        cache4.setName("Cache IV");
        cache4.setOwner("Mike");
        cache4.setType(Geocache.CacheType.Letterbox);
        cache4.setLongitude(7.7);
        cache4.setLatitude(8.8);
        cache4.setSize(Geocache.CacheSize.Large);
        cache4.setDifficulty(2.5f);
        cache4.setTerrain(3.5f);
        cache4.setPublished(new DateTime(2004, 7, 8, 03, 00));
        
        Geocache cache5 = new Geocache();
        cache5.setGcCode("GC555");
        cache5.setName("Cache V");
        cache5.setOwner("Jake");
        cache5.setType(Geocache.CacheType.Mystery);
        cache5.setLongitude(9.9);
        cache5.setLatitude(10.10);
        cache5.setSize(Geocache.CacheSize.Not_chosen);
        cache5.setDifficulty(3);
        cache5.setTerrain(3);
        cache5.setPublished(new DateTime(2005, 9, 10, 06, 07));
        
        caches.add(cache1);
        caches.add(cache2);
        caches.add(cache3);
        caches.add(cache4);
        caches.add(cache5);
    }
    
}
