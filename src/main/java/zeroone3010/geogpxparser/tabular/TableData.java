package zeroone3010.geogpxparser.tabular;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class that represents tabular data.
 */
@XmlRootElement(name = "table")
public class TableData {

    private List<TableRow> rows = new ArrayList<>();
    private final String identifier;

    public TableData() {
        this("unknownTable");
    }

    public TableData(String identifier) {
        this.identifier = identifier;
    }

    public void addRow(TableRow row) {
        this.rows.add(row);
    }

    @XmlElement(name = "row")
    public List<TableRow> getRows() {
        return this.rows;
    }

    @XmlAttribute(name = "identifier")
    public String getIdentifier() {
        return this.identifier;
    }
}
