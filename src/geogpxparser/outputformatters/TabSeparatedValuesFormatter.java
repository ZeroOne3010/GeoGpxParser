package geogpxparser.outputformatters;

import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import java.util.Iterator;

/**
 * Formats the given TableData object into a string of tab separated values.
 */
public class TabSeparatedValuesFormatter extends AbstractTabularDataFormatter {

    private static final String SEPARATOR = "\t";
    private static final String REPLACEMENT = " ";

    public TabSeparatedValuesFormatter(TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Iterator<TableRow> rowIter = getTable().getRows().iterator(); rowIter.hasNext();) {
            TableRow tableRow = rowIter.next();
            for (Iterator<CellData> colIter = tableRow.getCells().iterator(); colIter.hasNext();) {
                CellData cellData = colIter.next();
                sb.append(cellData.getText().replaceAll(SEPARATOR, REPLACEMENT));
                if (colIter.hasNext()) {
                    sb.append(SEPARATOR);
                }
            }

            if (rowIter.hasNext()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
