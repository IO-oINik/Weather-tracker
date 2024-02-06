package com.pet.project.weathertracker.models.api.version3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
