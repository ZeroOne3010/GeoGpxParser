package zeroone3010.geogpxparser.outputformatters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import zeroone3010.geogpxparser.tabular.CellData;
import zeroone3010.geogpxparser.tabular.TableData;
import zeroone3010.geogpxparser.tabular.TableRow;

/**
 * Tests the TabSeparatedValuesFormatter.
 */
public class TabSeparatedValuesFormatterTest {

    private TableData table;

    public TabSeparatedValuesFormatterTest() {
        table = new TableData("testTable");
        TableRow row1 = new TableRow(true);
        TableRow row2 = new TableRow(false);
        TableRow row3 = new TableRow(false);
        row1.addCell(new CellData("one"));
        row1.addCell(new CellData("two"));
        row1.addCell(new CellData("three"));

        row2.addCell(new CellData("four"));
        row2.addCell(new CellData("fi\tve"));
        row2.addCell(new CellData("six"));

        row3.addCell(new CellData("seven"));
        row3.addCell(new CellData("eight"));
        row3.addCell(new CellData("nine"));
        table.addRow(row1);
        table.addRow(row2);
        table.addRow(row3);
    }

    @Test
    public void test_formatting_a_table() {
        assertNotNull(table);

        String result = new TabSeparatedValuesFormatter(table).toString();
        assertEquals("one\ttwo\tthree\nfour\tfi ve\tsix\nseven\teight\tnine", result);
    }
}
