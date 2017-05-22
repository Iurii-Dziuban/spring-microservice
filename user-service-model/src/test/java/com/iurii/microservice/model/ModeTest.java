package com.iurii.microservice.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ModeTest {

    @Test
    public void testFindModeByName() {
        assertThat(Mode.findModeByName("update")).isEqualByComparingTo(Mode.UPDATE);
        assertThat(Mode.findModeByName("updateAmount")).isEqualByComparingTo(Mode.UPDATE_AMOUNT);
        assertThat(Mode.findModeByName("set")).isEqualByComparingTo(Mode.SET);
        assertThat(Mode.findModeByName(null)).isNull();
        assertThat(Mode.findModeByName("")).isNull();
        assertThat(Mode.valueOf("SET")).isEqualByComparingTo(Mode.SET);
        assertThat(Mode.SET.toString()).isEqualTo("set");
        assertThat(Mode.UPDATE.toString()).isEqualTo("update");
    }
}