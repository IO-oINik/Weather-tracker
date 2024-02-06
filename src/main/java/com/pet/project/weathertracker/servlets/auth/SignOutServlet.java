package com.pet.project.weathertracker.servlets.auth;

import com.pet.project.weathertracker.dao.SessionDao;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@WebServlet("/auth/sign-out")
public class SignOutServlet extends BaseServlet {
    private final SessionDao sessionDao = new SessionDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();
        Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(cookie1 -> cookie1.getName().equals("sessionId")).findFirst();
        if(cookieOptional.isPresent()) {
            Optional<Session> sessionOptional = sessionDao.getById(UUID.fromString(cookieOptional.get().getValue()));
            if(sessionOptional.isPresent()) {
                log.info("SignOut " + sessionOptional.get().getUser());
                sessionDao.delete(sessionOptional.get());
            }
        }
        resp.sendRedirect("/auth/sign-in");
    }
}
