package geogpxparser.tabular;

import static org.junit.Assert.*;
import org.junit.Test;

public class TableRowTest {

    static final TableRow hr1 = new TableRow(true);
    static final TableRow r1 = new TableRow(false);
    static final TableRow r2 = new TableRow(false);
    static final TableRow r3 = new TableRow(false);

    static {
        hr1.addCell(new CellData("Hello"));
        hr1.addCell(new CellData(null));
        hr1.addCell(new CellData("World", "http://example.net/"));

        r1.addCell(new CellData("Hello"));
        r1.addCell(new CellData(null));
        r1.addCell(new CellData("World", "http://example.net/"));

        r2.addCell(new CellData("Hello"));
        r2.addCell(new CellData(null));
        r2.addCell(new CellData("World", "http://example.net/"));

        r3.addCell(new CellData("Foo"));
        r3.addCell(new CellData(null));
        r3.addCell(new CellData("Bar", "http://example.net/"));
    }

    @Test
    public void equal_rows_should_be_equal() {
        assertEquals(r1, r2);
        assertEquals(r2, r1);
    }

    @Test
    public void unequal_rows_should_be_unequal() {
        assertNotSame(r1, r3);
        assertNotSame(r3, r1);
    }
    @Test
    public void header_row_and_non_header_row_should_not_be_equal() {
        assertNotSame(hr1, r1);
        assertNotSame(r1, hr1);
    }
}
