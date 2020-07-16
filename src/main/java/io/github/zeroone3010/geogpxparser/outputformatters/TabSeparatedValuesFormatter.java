package io.github.zeroone3010.geogpxparser.outputformatters;

import io.github.zeroone3010.geogpxparser.tabular.CellData;
import io.github.zeroone3010.geogpxparser.tabular.TableData;
import io.github.zeroone3010.geogpxparser.tabular.TableRow;

import java.util.Iterator;

/**
 * Formats the given TableData object into a string of tab separated values.
 */
class TabSeparatedValuesFormatter extends AbstractTabularDataFormatter {

    private static final String SEPARATOR = "\t";
    private static final String REPLACEMENT = " ";
    private static final String FILE_EXTENSION = "txt";

    public TabSeparatedValuesFormatter(final TableData data) {
        super(data);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (final Iterator<TableRow> rowIter = getTable().getRows().iterator(); rowIter.hasNext(); ) {
            final TableRow tableRow = rowIter.next();
            for (final Iterator<CellData> colIter = tableRow.getCells().iterator(); colIter.hasNext(); ) {
                final CellData cellData = colIter.next();
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

    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }
}
