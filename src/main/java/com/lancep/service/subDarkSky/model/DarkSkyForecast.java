
package com.lancep.service.subDarkSky.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Code autogenerated by submitting DarkSky json to http://www.jsonschema2pojo.org/
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "latitude",
    "longitude",
    "timezone",
    "offset",
    "currently",
    "hourly",
    "daily",
    "flags"
})
public class DarkSkyForecast {

    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("currently")
    @Valid
    private Currently currently;
    @JsonProperty("hourly")
    @Valid
    private Hourly hourly;
    @JsonProperty("daily")
    @Valid
    private Daily daily;
    @JsonProperty("flags")
    @Valid
    private Flags flags;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
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
