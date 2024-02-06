package com.pet.project.weathertracker.models.api.version2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Clouds {
    @JsonProperty("all")
    private int cloudiness;
}
