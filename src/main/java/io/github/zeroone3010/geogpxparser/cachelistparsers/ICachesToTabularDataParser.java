package io.github.zeroone3010.geogpxparser.cachelistparsers;

import java.util.List;

import io.github.zeroone3010.geogpxparser.Geocache;
import io.github.zeroone3010.geogpxparser.tabular.TableData;

/**
 * An interface for classes that can accept a List of Geocache objects and parse
 * them into tabular format.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public interface ICachesToTabularDataParser {

    public TableData getTabularInfo(final List<Geocache> caches);
}
