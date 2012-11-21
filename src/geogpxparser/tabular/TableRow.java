package geogpxparser.tabular;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a table row.
 */
public class TableRow {
    private final boolean header;
    private List<CellData> cells = new ArrayList<>();

    public TableRow(boolean header) {
        this.header = header;
    }
    
    public boolean isHeader() {
        return header;
    }

    public List<CellData> getCells() {
        return cells;
    }

    public void addCell(CellData cell) {
        this.cells.add(cell);
    }
}
