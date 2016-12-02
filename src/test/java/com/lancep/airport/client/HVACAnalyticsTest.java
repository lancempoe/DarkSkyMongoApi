package com.lancep.airport.client;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ConstantConditions")
public class HVACAnalyticsTest {

    private final HVACAnalytics subject = new HVACAnalytics();

    @Test
    public void canGetDay() {
        long day = 1234L;
        subject.setDay(day);
        assertThat(subject.getDay(), is(day));
    }

    @Test
    public void canGetCoolingUsed() {
        boolean isUsed = true;
        subject.setCoolingUsed(isUsed);
        assertThat(subject.isCoolingUsed(), is(isUsed));
    }

    @Test
    public void canGetHeatingUsed() {
        boolean isUsed = true;
        subject.setHeatingUsed(isUsed);
        assertThat(subject.isHeatingUsed(), is(isUsed));
    }
}