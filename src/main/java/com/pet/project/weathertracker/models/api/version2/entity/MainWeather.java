package com.pet.project.weathertracker.models.api.version2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MainWeather {
    private double temp;
    @JsonProperty("feels_like")
    private double feelsLike;
    private double pressure;
    private double humidity;
    @JsonProperty("temp_min")
    private double tempMin;
    @JsonProperty("temp_max")
    private double tempMax;
}
