package io.github.zeroone3010.geogpxparser;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CacheSizeTest {
    @Test
    public void getByGpxDescription_with_existing_sizes() {
        Arrays.asList(CacheSize.values()).stream().forEach(c -> {
            assertEquals(c, CacheSize.getByGpxDescription(c.getGpxDescription()));
        });
    }

    @Test
    public void getByGpxDescription_with_nonexisting_size() {
        assertEquals(CacheSize.Not_chosen, CacheSize.getByGpxDescription("Foobar"));
    }
}
