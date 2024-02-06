package com.pet.project.weathertracker.models.dto;

import com.pet.project.weathertracker.models.api.version2.ForecastApiResponse;
import com.pet.project.weathertracker.models.api.version2.WeatherApiResponse;
import com.pet.project.weathertracker.models.api.version2.entity.Forecast;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static WeatherDTO toDTO(WeatherApiResponse weatherApiResponse) {
        return WeatherDTO.builder()
                .main(weatherApiResponse.getWeathers().get(0).getMain())
                .description(weatherApiResponse.getWeathers().get(0).getDescription())
                .icon(weatherApiResponse.getWeathers().get(0).getIcon().substring(0, 2))
                .temp(weatherApiResponse.getMain().getTemp())
                .feelsLike(weatherApiResponse.getMain().getFeelsLike())
                .pressure(weatherApiResponse.getMain().getPressure())
                .humidity(weatherApiResponse.getMain().getHumidity())
                .tempMin(weatherApiResponse.getMain().getTempMin())
                .tempMax(weatherApiResponse.getMain().getTempMax())
                .windSpeed(weatherApiResponse.getWind().getSpeed())
                .cloudiness(weatherApiResponse.getClouds().getCloudiness())
                .dt(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.ofHours(weatherApiResponse.getTimezone() / 3600)))
                .sunrise(weatherApiResponse.getSys().getSunrise().toInstant().atOffset(ZoneOffset.ofHours(weatherApiResponse.getTimezone() / 3600)))
                .sunset(weatherApiResponse.getSys().getSunset().toInstant().atOffset(ZoneOffset.ofHours(weatherApiResponse.getTimezone() / 3600)))
                .timezone(weatherApiResponse.getTimezone())
                .name(weatherApiResponse.getName()).build();
    }
    public static List<WeatherDTO> toDTOs(ForecastApiResponse forecastApiResponse) {
        List<WeatherDTO> list = new ArrayList<>();
        for (var forecast: forecastApiResponse.getList()) {
            WeatherDTO weatherDTO = WeatherDTO.builder()
                    .main(forecast.getWeather().get(0).getMain())
                    .description(forecast.getWeather().get(0).getDescription())
                    .icon(forecast.getWeather().get(0).getIcon().substring(0, 2))
                    .temp(forecast.getMain().getTemp())
                    .feelsLike(forecast.getMain().getFeelsLike())
                    .pressure(forecast.getMain().getPressure())
                    .humidity(forecast.getMain().getHumidity())
                    .tempMin(forecast.getMain().getTempMin())
                    .tempMax(forecast.getMain().getTempMax())
                    .windSpeed(forecast.getWind().getSpeed())
                    .cloudiness(forecast.getClouds().getCloudiness())
                    .dt(forecast.getDt().toInstant().atOffset(ZoneOffset.ofHours(forecastApiResponse.getCity().getTimezone() / 3600)))
                    .sunrise(forecastApiResponse.getCity().getSunrise().toInstant().atOffset(ZoneOffset.ofHours(forecastApiResponse.getCity().getTimezone() / 3600)))
                    .sunset(forecastApiResponse.getCity().getSunset().toInstant().atOffset(ZoneOffset.ofHours(forecastApiResponse.getCity().getTimezone() / 3600)))
                    .timezone(forecastApiResponse.getCity().getTimezone())
                    .name(forecastApiResponse.getCity().getName()).build();
            list.add(weatherDTO);
        }
        return list;
    }
}
