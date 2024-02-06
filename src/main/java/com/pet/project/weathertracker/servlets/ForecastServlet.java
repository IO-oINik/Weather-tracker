package com.pet.project.weathertracker.servlets;

import com.pet.project.weathertracker.exceptions.InvalidParameterException;
import com.pet.project.weathertracker.exceptions.WeatherApiException;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.models.dto.WeatherDTO;
import com.pet.project.weathertracker.services.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@WebServlet("/forecast")
public class ForecastServlet extends BaseServlet {
    private final WeatherApiService weatherApiService = new WeatherApiService();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("View forecast for " + ((Session) req.getAttribute("session")).getUser());

        String name = req.getParameter("name");
        double lat, lon;
        try {
            lat = Double.parseDouble(req.getParameter("lat"));
            lon = Double.parseDouble(req.getParameter("lon"));
        } catch (NullPointerException | NumberFormatException e) {
            throw new InvalidParameterException("Invalid parameter 'lat' or 'lon'");
        }

        if(name==null || name.isBlank()) {
            throw new InvalidParameterException("Invalid parameter 'name'");
        }

        try {
            Map<LocalDate, List<WeatherDTO>> dailyWeathers = weatherApiService.getDailyWeatherByHourlyWeather(weatherApiService.getHourlyWeatherByLocation(lat, lon));
            List<WeatherDTO> currentDayWeathers = dailyWeathers.get(LocalDate.now());
            if(currentDayWeathers.size() < 5) {
                currentDayWeathers.addAll(dailyWeathers.get(LocalDate.now().plusDays(1)));
            }
            List<WeatherDTO> dailyOneWeathers = weatherApiService.getOneDailyWeatherByDailyWeather(dailyWeathers);
            context.setVariable("localeName", name);
            context.setVariable("dailyForecast", dailyOneWeathers);
            context.setVariable("currentDayWeathers", currentDayWeathers);
            templateEngine.process("forecast", context, resp.getWriter());
        } catch (WeatherApiException e) {
            log.error(e.getMessage());
            context.setVariable("error", "Что-то пошло не так...");
            templateEngine.process("forecast", context, resp.getWriter());
        }
    }
}
