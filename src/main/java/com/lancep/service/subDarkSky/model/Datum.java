
package com.lancep.service.subDarkSky.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * Code autogenerated by submitting DarkSky json to http://www.jsonschema2pojo.org/
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "time",
    "summary",
    "icon",
    "precipIntensity",
    "precipProbability",
    "temperature",
    "apparentTemperature",
    "dewPoint",
    "humidity",
    "windSpeed",
    "windBearing",
    "visibility",
    "cloudCover",
    "pressure"
})
class Datum {

    @JsonProperty("time")
    private Integer time;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("precipIntensity")
    private Integer precipIntensity;
    @JsonProperty("precipProbability")
    private Integer precipProbability;
    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("apparentTemperature")
    private Double apparentTemperature;
    @JsonProperty("dewPoint")
    private Double dewPoint;
    @JsonProperty("humidity")
    private Double humidity;
    @JsonProperty("windSpeed")
    private Double windSpeed;
    @JsonProperty("windBearing")
    private Integer windBearing;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("cloudCover")
    private Double cloudCover;
    @JsonProperty("pressure")
    private Double pressure;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(Integer precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public Integer getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Integer precipProbability) {
        this.precipProbability = precipProbability;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(Integer windBearing) {
        this.windBearing = windBearing;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
