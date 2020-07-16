package io.github.zeroone3010.geogpxparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeocacheTest {

    @Test
    public void getting_hint_encrypted_basic() {
        final boolean decrypted = false;
        final Geocache cache = Geocache.builder().hint("kiven alla").build();
        final String expResult = "xvira nyyn";
        final String result = cache.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_basic() {
        final Geocache cache = Geocache.builder().hint("kiven alla").build();
        final boolean decrypted = true;
        final String expResult = "kiven alla";
        final String result = cache.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_encrypted_with_non_alphabetic_chars() {
        final Geocache cache = Geocache.builder().hint("@[`{ KIVEN YLLÄ 123").build();
        final boolean decrypted = false;
        final String expResult = "@[`{ XVIRA LYYÄ 123";
        final String result = cache.getHint(decrypted);
        assertEquals(expResult, result);
    }

    @Test
    public void getting_hint_decrypted_with_non_alphabetic_chars() {
        final Geocache cache = Geocache.builder().hint("@[`{ KIVEN YLLÄ 123").build();
        final boolean decrypted = true;
        final String expResult = "@[`{ KIVEN YLLÄ 123";
        final String result = cache.getHint(decrypted);
        assertEquals(expResult, result);
    }
}
