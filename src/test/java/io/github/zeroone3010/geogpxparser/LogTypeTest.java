package io.github.zeroone3010.geogpxparser;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LogTypeTest {
    @Test
    public void getByGpxDescription_with_existing_types() {
        Arrays.asList(LogType.values()).stream().forEach(t -> {
            assertEquals(t, LogType.getByGpxDescription(t.getGpxDescription()));
        });
    }

    @Test
    public void getByGpxDescription_with_nonexisting_type() {
        assertEquals(LogType.OTHER, LogType.getByGpxDescription("Foobar"));
    }
}
