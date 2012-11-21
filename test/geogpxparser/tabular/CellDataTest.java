package geogpxparser.tabular;

import java.net.MalformedURLException;
import static org.junit.Assert.*;
import org.junit.Test;

public class CellDataTest {
    
    @Test
    public void test_normal_cell_creation() {
        CellData cell = new CellData.CellBuilder().text("hello").build();
        assertEquals("hello", cell.getText());
        assertNull(cell.getUrl());
    }
    
    @Test
    public void test_link_cell_creation() throws MalformedURLException {
        CellData cell = new CellData.CellBuilder().text("hello").url("http://www.example.com/foo").build();
        assertEquals("hello", cell.getText());
        assertEquals("http://www.example.com/foo", cell.getUrl().toString());
    }
}
