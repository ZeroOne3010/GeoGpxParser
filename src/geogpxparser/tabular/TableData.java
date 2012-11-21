package geogpxparser.tabular;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents tabular data.
 */
public class TableData {
    private List<TableRow> rows = new ArrayList<>();
    
    public void addRow(TableRow row) {
        this.rows.add(row);
    }
    
    public List<TableRow> getRows() {
        return this.rows;
    }
}
