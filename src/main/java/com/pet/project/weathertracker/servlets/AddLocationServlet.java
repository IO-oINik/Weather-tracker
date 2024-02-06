package com.pet.project.weathertracker.servlets;

import com.pet.project.weathertracker.dao.UserDao;
import com.pet.project.weathertracker.exceptions.InvalidParameterException;
import com.pet.project.weathertracker.models.Location;
import com.pet.project.weathertracker.models.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/add-location")
public class AddLocationServlet extends BaseServlet{
    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Adding location for " + ((Session) req.getAttribute("session")).getUser());

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

        Session session = (Session) req.getAttribute("session");
        Location location = new Location(name, lat, lon);

        log.info("Adding a " + session.getUser() + " to a new location");

        userDao.addLocationToUser(session.getUser(), location);


        resp.sendRedirect("/");
    }
}
