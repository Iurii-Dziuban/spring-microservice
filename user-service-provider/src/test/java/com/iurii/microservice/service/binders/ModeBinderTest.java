package com.iurii.microservice.service.binders;

import com.iurii.microservice.model.Mode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModeBinderTest {

    private final ModeBinder modeBinder = new ModeBinder();

    @Test
    public void testSet() {
        modeBinder.setAsText("set");
        assertThat(Mode.SET).isEqualTo(modeBinder.getValue());
    }

    @Test
    public void testAdd() {
        modeBinder.setAsText("update");
        assertThat(Mode.UPDATE).isEqualTo(modeBinder.getValue());
    }

    @Test
    public void testEmpty() {
        modeBinder.setAsText("");
        assertThat(modeBinder.getValue()).isNull();
    }

    @Test
    public void testNull() {
        modeBinder.setAsText(null);
        assertThat(modeBinder.getValue()).isNull();
    }
}
