package zeroone3010.geogpxparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the unit test class for the Geocache class.
 *
 * @author Ville Saalo (http://coord.info/PR32K8V)
 */
public class GeocacheTest {
    final Geocache instance = new Geocache();

    @Test
    public void getting_hint_encrypted_basic() {
        final boolean decrypted = false;
        instance.setHint("kiven alla");
        final String expResult = "xvira nyyn";
        final String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_basic() {
        instance.setHint("kiven alla");
        final boolean decrypted = true;
        final String expResult = "kiven alla";
        final String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_encrypted_with_non_alphabetic_chars() {
        instance.setHint("@[`{ KIVEN YLLÄ 123");
        final boolean decrypted = false;
        final String expResult = "@[`{ XVIRA LYYÄ 123";
        final String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_with_non_alphabetic_chars() {
        instance.setHint("@[`{ KIVEN YLLÄ 123");
        final boolean decrypted = true;
        final String expResult = "@[`{ KIVEN YLLÄ 123";
        final String result = instance.getHint(decrypted);
        assertEquals(expResult, result);
    }
}
