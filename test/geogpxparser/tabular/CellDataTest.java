package geogpxparser.tabular;

import static org.junit.Assert.*;
import org.junit.Test;

public class CellDataTest {

    @Test
    public void test_normal_cell_creation() {
        CellData cell = new CellData("hello");
        assertEquals("hello", cell.getText());
        assertNull(cell.getUrl());
    }

    @Test
    public void test_link_cell_creation() {
        CellData cell = new CellData("hello", "http://www.example.com/foo");
        assertEquals("hello", cell.getText());
        assertEquals("http://www.example.com/foo", cell.getUrl().toString());
    }

    @Test
    public void malformed_url_should_leave_url_as_null() {
        CellData cell = new CellData("hello", "omg : bbq ? foo / test");
        assertEquals("hello", cell.getText());
        assertNull(cell.getUrl());
    }
}
