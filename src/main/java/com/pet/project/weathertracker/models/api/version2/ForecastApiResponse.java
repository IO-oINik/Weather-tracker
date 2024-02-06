package com.pet.project.weathertracker.models.api.version2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pet.project.weathertracker.models.api.util.UnixTimestampDeserializer;
import com.pet.project.weathertracker.models.api.version2.entity.Forecast;
import com.pet.project.weathertracker.models.api.version2.entity.MainWeather;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ForecastApiResponse {
    private List<Forecast> list;
    private City city;
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class City {
        private String name;
        private int timezone;
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private OffsetDateTime sunrise;
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private OffsetDateTime sunset;
    }
}
