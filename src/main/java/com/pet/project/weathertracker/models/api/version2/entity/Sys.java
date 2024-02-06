package com.pet.project.weathertracker.models.api.version2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pet.project.weathertracker.models.api.util.UnixTimestampDeserializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Sys {
    private String country;
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private OffsetDateTime sunrise;
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private OffsetDateTime sunset;
}
