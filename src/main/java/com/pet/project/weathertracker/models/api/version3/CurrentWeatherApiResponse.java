package com.pet.project.weathertracker.models.api.version3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pet.project.weathertracker.models.api.version3.entity.CurrentWeather;
import com.pet.project.weathertracker.models.api.util.TimezoneOffsetDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherApiResponse {
    private double lat;
    private double lon;
    private String timezone;
    @JsonProperty("timezone_offset")
    @JsonDeserialize(using = TimezoneOffsetDeserializer.class)
    private int timezoneOffset;
    private CurrentWeather current;
}
