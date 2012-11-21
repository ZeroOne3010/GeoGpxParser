package geogpxparser;

import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;

/**
 * Formats the given TableData object into a plain text file.
 */
public class TabSeparatedValuesFormatter extends AbstractTabularDataFormatter {
    private static final String SEPARATOR = "\t";
    
    public TabSeparatedValuesFormatter(TableData data) {
        super(data);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for (TableRow tableRow : getTable().getRows()) {
            for (CellData cellData : tableRow.getCells()) {
                sb.append(cellData.getText()).append(SEPARATOR);
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
}
