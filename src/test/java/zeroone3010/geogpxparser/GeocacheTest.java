package zeroone3010.geogpxparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the unit test class for the Geocache class.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class GeocacheTest {

    @Test
    public void testHint() {
        System.out.println("testHint");

        // Test the basic case of getting the hint as encrypted:
        boolean decrypted = false;
        Geocache instance = new Geocache();
        instance.setHint("kiven alla");
        String expResult = "xvira nyyn";
        String result = instance.getHint(decrypted);
        assertEquals(expResult, result);

        // Test the basic case of getting the hint as decrypted:
        expResult = "kiven alla";
        decrypted = true;
        result = instance.getHint(decrypted);
        assertEquals(expResult, result);

        // Test getting the encrypted hint with some non-alphabetic characters:
        instance.setHint("@[`{ KIVEN YLLÄ 123");
        expResult = "@[`{ XVIRA LYYÄ 123";
        decrypted = false;
        result = instance.getHint(decrypted);
        assertEquals(expResult, result);

        // Test getting the decrypted hint with some non-alphabetic characters:
        expResult = "@[`{ KIVEN YLLÄ 123";
        decrypted = true;
        result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }
    
    @Test
    public void test_northern_latitudeString() {
        Geocache instance = new Geocache();
        instance.setLatitude(60.16872);
        assertEquals("N 60° 10.123'", instance.getLatitudeString());
    }

    @Test
    public void test_southern_latitudeString() {
        Geocache instance = new Geocache();
        instance.setLatitude(-15.42292);
        assertEquals("S 15° 25.375'", instance.getLatitudeString());
    }
    
    @Test
    public void test_eastern_longitudeString() {
        Geocache instance = new Geocache();
        instance.setLongitude(24.41667);
        assertEquals("E 24° 25.000'", instance.getLongitudeString());
    }
    
    @Test
    public void test_western_longitudeString() {
        Geocache instance = new Geocache();
        instance.setLongitude(-100.20575);
        assertEquals("W 100° 12.345'", instance.getLongitudeString());
    }
}
