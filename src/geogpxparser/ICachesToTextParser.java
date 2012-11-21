package geogpxparser;

import geogpxparser.tabular.TableData;
import java.util.List;

/**
 * An interface for classes that can accept a List of Geocache objects and parse
 * them into tabular format.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public interface ICachesToTextParser {

    public TableData getTabularInfo(final List<Geocache> caches);
}
