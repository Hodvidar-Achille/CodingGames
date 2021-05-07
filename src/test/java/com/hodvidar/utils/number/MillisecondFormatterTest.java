package com.hodvidar.utils.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MillisecondFormatterTest {
    @Test
    public void checkTime() {
        String r = MillisecondeFormater.asTime(999);
        assertThat(r).isEqualTo("0 hour(s) 0 minute(s) 0 seconde(s) 999 ms");

        r = MillisecondeFormater.asTime(59999);
        assertThat(r).isEqualTo("0 hour(s) 0 minute(s) 59 seconde(s) 999 ms");

        r = MillisecondeFormater.asTime(3599999);
        assertThat(r).isEqualTo("0 hour(s) 59 minute(s) 59 seconde(s) 999 ms");

        r = MillisecondeFormater.asTime(3600000);
        assertThat(r).isEqualTo("1 hour(s) 0 minute(s) 0 seconde(s) 0 ms");

        r = MillisecondeFormater.asTime(10799999);
        assertThat(r).isEqualTo("2 hour(s) 59 minute(s) 59 seconde(s) 999 ms");
    }
}
