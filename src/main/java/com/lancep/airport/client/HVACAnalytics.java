package com.lancep.airport.client;

public class HVACAnalytics {

    private Long day;
    private boolean coolingUsed;
    private boolean heatingUsed;

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public boolean isCoolingUsed() {
        return coolingUsed;
    }

    public void setCoolingUsed(boolean coolingUsed) {
        this.coolingUsed = coolingUsed;
    }

    public boolean isHeatingUsed() {
        return heatingUsed;
    }

    public void setHeatingUsed(boolean heatingUsed) {
        this.heatingUsed = heatingUsed;
    }
}
