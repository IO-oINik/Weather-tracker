package com.pet.project.weathertracker.models.api.version2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pet.project.weathertracker.models.api.util.UnixTimestampDeserializer;
import com.pet.project.weathertracker.models.api.version3.entity.Weather;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Forecast {
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private OffsetDateTime dt;
    private MainWeather main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
}
