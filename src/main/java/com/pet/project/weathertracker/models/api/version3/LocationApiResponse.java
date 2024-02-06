package com.pet.project.weathertracker.models.api.version3;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
public class LocationApiResponse {
    private String name;
    private double lat;
    private double lon;
    private String country;
    @JsonProperty("local_names")
    private Localization localization;
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    public static class Localization {
        String en;
        String ru;
    }
}
