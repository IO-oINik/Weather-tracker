package com.pet.project.weathertracker.servlets;

import com.pet.project.weathertracker.exceptions.GeocodingApiException;
import com.pet.project.weathertracker.exceptions.WeatherApiException;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.models.api.version3.LocationApiResponse;
import com.pet.project.weathertracker.models.dto.WeatherDTO;
import com.pet.project.weathertracker.services.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/search-locations")
public class SearchLocationsServlet extends BaseServlet{
    private final WeatherApiService weatherApiService = new WeatherApiService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Searching location for " + ((Session) req.getAttribute("session")).getUser());

        String q = req.getParameter("q");
        if(q==null || q.isBlank()) {
            context.setVariable("error", "Поле поиска пустое");
            templateEngine.process("search-locations", context, resp.getWriter());
            return;
        }

        try {
            List<LocationApiResponse> list = weatherApiService.searchLocationByName(q);
            List<WeatherDTO> locations = weatherApiService.getWeathersByLocationsApiResponse(list);
            context.setVariable("locations", locations);
            templateEngine.process("search-locations", context, resp.getWriter());
        } catch (GeocodingApiException | WeatherApiException e) {
            log.error(e.getMessage());
            context.setVariable("error", "Что-то пошло не так...");
            templateEngine.process("search-locations", context, resp.getWriter());
        }
    }
}
