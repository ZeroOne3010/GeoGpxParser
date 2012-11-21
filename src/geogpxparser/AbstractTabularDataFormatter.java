package geogpxparser;

import geogpxparser.tabular.TableData;

/**
 * This class defines methods for formatting tabular data.
 */
public abstract class AbstractTabularDataFormatter {
    private final TableData table;
    public AbstractTabularDataFormatter(TableData data) {
        this.table = data;
    }
    
    protected TableData getTable() {
        return this.table;
    }
    
    @Override
    public abstract String toString();
}
