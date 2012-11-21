package geogpxparser;

import geogpxparser.tabular.CellData;
import geogpxparser.tabular.TableData;
import geogpxparser.tabular.TableRow;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the TabSeparatedValuesFormatter.
 */
public class TabSeparatedValuesFormatterTest {
    
    private TableData table;
    
    public TabSeparatedValuesFormatterTest() {
        table = new TableData();
        TableRow row1 = new TableRow(true);
        TableRow row2 = new TableRow(false);
        TableRow row3 = new TableRow(false);
        row1.addCell(new CellData.CellBuilder().text("one").build());
        row1.addCell(new CellData.CellBuilder().text("two").build());
        row1.addCell(new CellData.CellBuilder().text("three").build());
        
        row2.addCell(new CellData.CellBuilder().text("four").build());
        row2.addCell(new CellData.CellBuilder().text("fi\tve").build());
        row2.addCell(new CellData.CellBuilder().text("six").build());
        
        row3.addCell(new CellData.CellBuilder().text("seven").build());
        row3.addCell(new CellData.CellBuilder().text("eight").build());
        row3.addCell(new CellData.CellBuilder().text("nine").build());
        table.addRow(row1);
        table.addRow(row2);
        table.addRow(row3);
    }
    
    @Test
    public void testSomeMethod() {
        assertNotNull(table);
        
        String result = new TabSeparatedValuesFormatter(table).toString();
        assertEquals("one\ttwo\tthree\nfour\tfi ve\tsix\nseven\teight\tnine", result);
    }
}
