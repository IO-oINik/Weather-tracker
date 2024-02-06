package com.pet.project.weathertracker.servlets.auth;

import com.password4j.Hash;
import com.password4j.Password;
import com.pet.project.weathertracker.dao.SessionDao;
import com.pet.project.weathertracker.dao.UserDao;
import com.pet.project.weathertracker.exceptions.EntityExistException;
import com.pet.project.weathertracker.exceptions.InvalidParameterException;
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

@Slf4j
@WebServlet("/auth/sign-up")
public class SignUpServlet extends BaseServlet {
    private final UserDao userDao = new UserDao();
    private final SessionDao sessionDao = new SessionDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("sign-up", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws InvalidParameterException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login == null || login.isBlank()) {
            throw new InvalidParameterException("Parameter login is invalid");
        }

        if(password == null || password.isBlank()) {
            throw new InvalidParameterException("Parameter password is invalid");
        }

        Hash hash = Password.hash(password).withBcrypt();
        User user = new User(login, hash.getResult());

        log.info("SignUp " + user);
        try {
            userDao.save(user);

            Session session = new Session(user);
            sessionDao.save(session);

            Cookie cookie = new Cookie("sessionId", session.getId().toString());
            cookie.setMaxAge(60*60*24);
            cookie.setPath("/");
            resp.addCookie(cookie);

            resp.sendRedirect("/");
        } catch (EntityExistException e) {
            log.info(e.getMessage());
            context.setVariable("error", "Пользователь уже существует");
            templateEngine.process("sign-up", context, resp.getWriter());
        } catch (Exception e) {
            log.info(e.getMessage());
            context.setVariable("error", "Что-то пошло не так...");
            templateEngine.process("sign-up", context, resp.getWriter());
        }
    }
}
