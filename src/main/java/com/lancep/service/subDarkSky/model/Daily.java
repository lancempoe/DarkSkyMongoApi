
package com.lancep.service.subDarkSky.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Code autogenerated by submitting DarkSky json to http://www.jsonschema2pojo.org/
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "data"
})
public class Daily {

    @JsonProperty("data")
    @Valid
    private List<Datum_> data = new ArrayList<Datum_>();
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Datum_> getData() {
        return data;
    }

    public void setData(List<Datum_> data) {
        this.data = data;
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
