package geogpxparser.tabular;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a table row.
 */
public class TableRow {
    private final boolean header;
    private final List<CellData> cells = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TableRow)) {
            return false;
        }
        TableRow other = (TableRow) o;
        if (other.getCells().size() != getCells().size()) {
            return false;
        }
        if (other.isHeader() != isHeader()) {
            return false;
        }
        for (int i = 0; i < cells.size(); i++) {
            CellData thisCell = cells.get(i);
            CellData otherCell = other.getCells().get(i);
            if (!thisCell.equals(otherCell)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.header ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.cells);
        return hash;
    }
}
