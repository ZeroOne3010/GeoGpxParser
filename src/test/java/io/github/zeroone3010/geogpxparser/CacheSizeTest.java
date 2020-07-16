package io.github.zeroone3010.geogpxparser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

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
