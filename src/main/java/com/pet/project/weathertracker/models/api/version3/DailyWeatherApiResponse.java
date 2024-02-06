package com.pet.project.weathertracker.models.api.version3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pet.project.weathertracker.models.api.version3.entity.DailyWeather;
import com.pet.project.weathertracker.models.api.util.TimezoneOffsetDeserializer;

import java.util.List;

public class DailyWeatherApiResponse {
    private double lat;
    private double lon;
    private String timezone;
    @JsonProperty("timezone_offset")
    @JsonDeserialize(using = TimezoneOffsetDeserializer.class)
    private int timezoneOffset;
    private List<DailyWeather> daily;
}
