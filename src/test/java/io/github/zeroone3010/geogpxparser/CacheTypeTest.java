package io.github.zeroone3010.geogpxparser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class CacheTypeTest {
    @Test
    public void getByGpxDescription_with_existing_types() {
        Arrays.asList(CacheType.values()).stream().forEach(c -> {
            assertEquals(c, CacheType.getByGpxDescription(c.getGpxDescription()));
        });
    }

    @Test
    public void getByGpxDescription_with_nonexisting_type() {
        assertEquals(CacheType.Other, CacheType.getByGpxDescription("Foobar"));
    }
}
