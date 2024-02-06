package com.pet.project.weathertracker.servlets;

import com.pet.project.weathertracker.exceptions.WeatherApiException;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.models.User;
import com.pet.project.weathertracker.models.dto.WeatherDTO;
import com.pet.project.weathertracker.services.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("")
public class HomeServlet extends BaseServlet{
    private final WeatherApiService weatherApiService = new WeatherApiService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = (Session) req.getAttribute("session");
        User user = session.getUser();

        log.info("View saved location for " + user);

        try {
            List<WeatherDTO> locations = weatherApiService.getWeathersByLocations(user.getLocations());
            context.setVariable("locations", locations);
            templateEngine.process("index", context, resp.getWriter());
        } catch (WeatherApiException e) {
            log.error(e.getMessage());
            context.setVariable("error", "Что-то пошло не так...");
            templateEngine.process("index", context, resp.getWriter());
        }
    }
}
