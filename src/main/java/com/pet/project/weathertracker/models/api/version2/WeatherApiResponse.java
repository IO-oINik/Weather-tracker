package com.pet.project.weathertracker.models.api.version2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.pet.project.weathertracker.models.api.util.UnixTimestampDeserializer;
import com.pet.project.weathertracker.models.api.version2.entity.Clouds;
import com.pet.project.weathertracker.models.api.version2.entity.MainWeather;
import com.pet.project.weathertracker.models.api.version2.entity.Sys;
import com.pet.project.weathertracker.models.api.version2.entity.Wind;
import com.pet.project.weathertracker.models.api.version3.entity.Weather;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherApiResponse {
    @JsonProperty("weather")
    private List<Weather> weathers;
    private MainWeather main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private OffsetDateTime dt;
    private Sys sys;
    private int timezone;
    private String name;

/*    public OffsetDateTime getDt() {
        ZoneOffset zoneOffset = ZoneOffset.ofHours(timezone/3600);
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(dt), zoneOffset);
    }*/
}
