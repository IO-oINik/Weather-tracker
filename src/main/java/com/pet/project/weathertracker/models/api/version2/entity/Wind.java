package com.pet.project.weathertracker.models.api.version2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private double speed;
    private double deg;
    private double gust;

}
