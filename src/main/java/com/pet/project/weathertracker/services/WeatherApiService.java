package com.pet.project.weathertracker.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.project.weathertracker.exceptions.GeocodingApiException;
import com.pet.project.weathertracker.exceptions.WeatherApiException;
import com.pet.project.weathertracker.models.Location;
import com.pet.project.weathertracker.models.api.version2.ForecastApiResponse;
import com.pet.project.weathertracker.models.api.version2.WeatherApiResponse;
import com.pet.project.weathertracker.models.api.version3.LocationApiResponse;
import com.pet.project.weathertracker.models.api.version3.DailyWeatherApiResponse;
import com.pet.project.weathertracker.models.dto.Mapper;
import com.pet.project.weathertracker.models.dto.WeatherDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherApiService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String BASE_URI_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private final String BASE_URI_GEOCODING = "https://api.openweathermap.org/geo/1.0/direct";
    private final String BASE_URI_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";
    private final String API_KEY = "***";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<LocationApiResponse> searchLocationByName(String name) throws GeocodingApiException {
        URI uri = URI.create(BASE_URI_GEOCODING + "?appid=" + API_KEY + "&q=" + name.replace(" ", "%20") + "&limit=5");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)).GET().build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() != 200)
                throw new GeocodingApiException("\"Issues with calling geocoding api for name = " + name + " status_code=" + httpResponse.statusCode());
            return objectMapper.readValue(httpResponse.body(), new TypeReference<List<LocationApiResponse>>() {});
        } catch (IOException | InterruptedException e) {
            throw new GeocodingApiException("\"Issues with calling geocoding api for name = " + name);
        } catch (GeocodingApiException e) {
            throw new GeocodingApiException(e.getMessage());
        }
    }

    private WeatherApiResponse getWeatherByCoordinates(double lat, double lon) throws WeatherApiException {
        URI uri = URI.create(BASE_URI_WEATHER +
                "?appid=" + API_KEY +
                "&lat=" + lat +
                "&lon=" + lon +
                "&units=metric" +
                "&lang=ru");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)).GET().build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() != 200)
                throw new WeatherApiException("Issues with calling weather api for coordinates = (" + lat + "," + lon +") status_code = " + httpResponse.statusCode());
            return objectMapper.readValue(httpResponse.body(), WeatherApiResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new WeatherApiException("Issues with calling weather api for coordinates = (" + lat + "," + lon +")");
        } catch (WeatherApiException e) {
            throw new WeatherApiException(e.getMessage());
        }
    }

    public List<WeatherDTO> getWeathersByLocationsApiResponse(List<LocationApiResponse> list) throws WeatherApiException {
        List<WeatherDTO> weathers = new ArrayList<>();
        for (var location: list) {
            WeatherApiResponse response = getWeatherByCoordinates(location.getLat(), location.getLon());
            WeatherDTO weatherDTO = Mapper.toDTO(response);
            if(location.getLocalization() != null && location.getLocalization().getRu() != null)
                weatherDTO.setName(location.getLocalization().getRu());
            weatherDTO.setLat(location.getLat());
            weatherDTO.setLon(location.getLon());
            weathers.add(weatherDTO);
        }
        return weathers;
    }

    public List<WeatherDTO> getWeathersByLocations(List<Location> list) throws WeatherApiException {
        List<WeatherDTO> weathers = new ArrayList<>();
        for (var location: list) {
            WeatherApiResponse response = getWeatherByCoordinates(location.getLat(), location.getLon());
            WeatherDTO weatherDTO = Mapper.toDTO(response);
            weatherDTO.setName(location.getName());
            weatherDTO.setLat(location.getLat());
            weatherDTO.setLon(location.getLon());
            weathers.add(weatherDTO);
        }
        return weathers;
    }

    private ForecastApiResponse getForecastByLocation(double lat, double lon) throws WeatherApiException {
        URI uri = URI.create(BASE_URI_FORECAST +
                "?appid=" + API_KEY +
                "&lat=" + lat +
                "&lon=" + lon +
                "&units=metric" +
                "&lang=ru");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)).GET().build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() != 200)
                throw new WeatherApiException("Issues with calling weather api for coordinates = (" + lat + "," + lon +") status_code = " + httpResponse.statusCode());
            return objectMapper.readValue(httpResponse.body(), ForecastApiResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new WeatherApiException("Issues with calling weather api for coordinates = (" + lat + "," + lon +")");
        } catch (WeatherApiException e) {
            throw new WeatherApiException(e.getMessage());
        }
    }

    public List<WeatherDTO> getHourlyWeatherByLocation(double lat, double lon) throws WeatherApiException {
        ForecastApiResponse forecastApiResponse = getForecastByLocation(lat, lon);
        return Mapper.toDTOs(forecastApiResponse);
    }

    public Map<LocalDate, List<WeatherDTO>> getDailyWeatherByHourlyWeather(List<WeatherDTO> hourlyWeather) {
        Map<LocalDate, List<WeatherDTO>> dailyForecast = new HashMap<>();

        LocalDate currentDay;

        for (var forecast: hourlyWeather) {
            currentDay = forecast.getDt().toLocalDate();
            if(dailyForecast.get(currentDay) == null) {
                List<WeatherDTO> list = new ArrayList<>();
                list.add(forecast);
                dailyForecast.put(currentDay, list);
            } else {
                dailyForecast.get(currentDay).add(forecast);
            }
        }
        return dailyForecast;
    }

    public List<WeatherDTO> getOneDailyWeatherByDailyWeather(Map<LocalDate, List<WeatherDTO>> map) {
        List<WeatherDTO> list = new ArrayList<>();

        for (var currentDate: map.keySet()) {
            list.add(map.get(currentDate).get(map.get(currentDate).size() / 2));
        }
        return list;
    }

    private DailyWeatherApiResponse getDailyWeatherByCoordinates(double lat, double lon) throws WeatherApiException {
        URI uri = URI.create(BASE_URI_WEATHER +
                "?appid=" + API_KEY +
                "&lat=" + lat +
                "&lon=" + lon +
                "&exclude=current,minutely,hourly,alerts" +
                "&units=metric" +
                "&lang=ru");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(10)).GET().build();
        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(httpResponse.body(), DailyWeatherApiResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new WeatherApiException("Issues with calling weather api for coordinates = (" + lat + "," + lon +")");
        }
    }

}
