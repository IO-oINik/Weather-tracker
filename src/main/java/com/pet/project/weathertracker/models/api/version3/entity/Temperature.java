package com.pet.project.weathertracker.models.api.version3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature {
    @JsonProperty("morn")
    double morning;
    double day;
    @JsonProperty("eve")
    double evening;
    double night;
}
