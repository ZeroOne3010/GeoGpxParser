package zeroone3010.geogpxparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the unit test class for the Geocache class.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class GeocacheTest {
    Geocache instance = new Geocache();

    @Test
    public void getting_hint_encrypted_basic() {
        boolean decrypted = false;
        instance.setHint("kiven alla");
        String expResult = "xvira nyyn";
        String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_basic() {
        instance.setHint("kiven alla");
        boolean decrypted = true;
        String expResult = "kiven alla";
        String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_encrypted_with_non_alphabetic_chars() {
        instance.setHint("@[`{ KIVEN YLLÄ 123");
        boolean decrypted = false;
        String expResult = "@[`{ XVIRA LYYÄ 123";
        String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_with_non_alphabetic_chars() {
        instance.setHint("@[`{ KIVEN YLLÄ 123");
        boolean decrypted = true;
        String expResult = "@[`{ KIVEN YLLÄ 123";
        String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void test_northern_latitudeString() {
        instance.setLatitude(60.16872);
        assertEquals("N 60° 10.123'", instance.getLatitudeString());
    }

    @Test
    public void test_southern_latitudeString() {
        instance.setLatitude(-15.42292);
        assertEquals("S 15° 25.375'", instance.getLatitudeString());
    }

    @Test
    public void test_eastern_longitudeString() {
        instance.setLongitude(24.41667);
        assertEquals("E 24° 25.000'", instance.getLongitudeString());
    }

    @Test
    public void test_western_longitudeString() {
        instance.setLongitude(-100.20575);
        assertEquals("W 100° 12.345'", instance.getLongitudeString());
    }
}
