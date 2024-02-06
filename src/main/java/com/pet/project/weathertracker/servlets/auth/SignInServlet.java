package com.pet.project.weathertracker.servlets.auth;

import com.password4j.Hash;
import com.password4j.Password;
import com.pet.project.weathertracker.dao.SessionDao;
import com.pet.project.weathertracker.dao.UserDao;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.models.User;
import com.pet.project.weathertracker.servlets.BaseServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet("/auth/sign-in")
public class SignInServlet extends BaseServlet {
    private final UserDao userDao = new UserDao();
    private final SessionDao sessionDao = new SessionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        templateEngine.process("sign-in", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Logging User");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login == null || login.isBlank() || password == null || password.isBlank()) {
            context.setVariable("error", "Неверный логин или пароль");
            templateEngine.process("sign-in", context, resp.getWriter());
            return;
        }


        Optional<User> userOptional = userDao.findByLogin(login);
        if(userOptional.isEmpty() || !Password.check(password, userOptional.get().getPassword()).withBcrypt()) {
            context.setVariable("error", "Неверный логин или пароль");
            templateEngine.process("sign-in", context, resp.getWriter());
            return;
        }
        Session session = new Session(userOptional.get());
        sessionDao.saveOrUpdate(session);

        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        resp.addCookie(cookie);

        resp.sendRedirect("/");
    }
}
