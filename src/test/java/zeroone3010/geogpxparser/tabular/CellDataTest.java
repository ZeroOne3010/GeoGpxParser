package zeroone3010.geogpxparser.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class CellDataTest {

    @Test
    public void test_normal_cell_creation() {
        final CellData cell = new CellData("hello");
        assertEquals("hello", cell.getText());
        assertNull(cell.getUrl());
    }

    @Test
    public void test_link_cell_creation() {
        final CellData cell = new CellData("hello", "http://www.example.com/foo");
        assertEquals("hello", cell.getText());
        assertEquals("http://www.example.com/foo", cell.getUrl().toString());
    }

    @Test
    public void malformed_url_should_leave_url_as_null() {
        final CellData cell = new CellData("hello", "omg : bbq ? foo / test");
        assertEquals("hello", cell.getText());
        assertNull(cell.getUrl());
    }

    @RunWith(Parameterized.class)
    public static class Equals_with_equal_values {

        @Parameters
        public static Collection<Object[]> data() {
            final Object[][] data = new Object[][]{ //
                {new CellData("hello"), new CellData("hello")}, //
                {new CellData(""), new CellData("")}, //
                {new CellData("abc", "http://example.com/foo"), new CellData("abc", "http://example.com/foo")}, //
                {new CellData("abc", null), new CellData("abc", null)}, //
                {new CellData(null, null), new CellData(null, null)}, //
            };
            return Arrays.asList(data);
        }
        private final CellData a;
        private final CellData b;

        public Equals_with_equal_values(final CellData a, final CellData b) {
            this.a = a;
            this.b = b;
        }

        @Test
        public void should_be_equal() {
            assertEquals(a, b);
            assertEquals(b, a);
        }
    }

    @RunWith(Parameterized.class)
    public static class Equals_with_unequal_values {

        @Parameters
        public static Collection<Object[]> data() {
            final Object[][] data = new Object[][]{ //
                {new CellData("aaa"), new CellData("bbb")}, //
                {new CellData("abc"), new CellData(null)}, //
                {new CellData("abc", "http://example.com/foo"), new CellData("abc", "http://example.com/bar")}, //
                {new CellData("abc", null), new CellData("abc", "http://example.org/")}, //
                {new CellData("abc", null), new CellData(null, "http://example.org/")}, //
            };
            return Arrays.asList(data);
        }
        private final CellData a;
        private final CellData b;

        public Equals_with_unequal_values(final CellData a, final CellData b) {
            this.a = a;
            this.b = b;
        }

        @Test
        public void should_not_be_equal() {
            assertNotSame(a, b);
            assertNotSame(b, a);
        }
    }
}
