package geogpxparser.tabular;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class that represents tabular data.
 */
@XmlRootElement(name = "table")
public class TableData {

    private List<TableRow> rows = new ArrayList<>();

    public void addRow(TableRow row) {
        this.rows.add(row);
    }

    @XmlElement(name = "row")
    public List<TableRow> getRows() {
        return this.rows;
    }
}
