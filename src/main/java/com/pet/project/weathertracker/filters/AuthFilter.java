package com.pet.project.weathertracker.filters;

import com.pet.project.weathertracker.dao.SessionDao;
import com.pet.project.weathertracker.exceptions.NotFoundCookieException;
import com.pet.project.weathertracker.exceptions.SessionExpiredException;
import com.pet.project.weathertracker.models.Session;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    public final SessionDao sessionDao = new SessionDao();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Verification of authorization");
        String path = ((HttpServletRequest)servletRequest).getRequestURI();
        if(!path.startsWith("/auth")) {
            try {
                Session session = getSession(servletRequest);
                servletRequest.setAttribute("session", session);
            } catch (NotFoundCookieException | SessionExpiredException e) {
                log.info("Authorization error " + e.getMessage());
                ((HttpServletResponse) servletResponse).sendRedirect("/auth/sign-in");
                return;
            }
        } else if (!path.startsWith("/auth/sign-out") && path.startsWith("/auth")) {
            try {
                Session session = getSession(servletRequest);
                ((HttpServletResponse) servletResponse).sendRedirect("/");
                return;
            } catch (NotFoundCookieException | SessionExpiredException ignored) {

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Session getSession(ServletRequest servletRequest) throws SessionExpiredException, NotFoundCookieException {
        log.info("Getting a session from cookies");
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        if(cookies == null) {
            throw new NotFoundCookieException("Cookie with session is not found");
        }
        Cookie cookie = Arrays.stream(cookies).filter(cookie1 -> {
            return cookie1.getName().equals("sessionId");
        }).findFirst().orElseThrow(() -> new NotFoundCookieException("Cookie with session is not found"));

        UUID sessionId = UUID.fromString(cookie.getValue());
        Optional<Session> sessionOptional = sessionDao.getById(sessionId);
        if(sessionOptional.isEmpty() || sessionOptional.get().isExpired()) {
            throw new SessionExpiredException("Session with id=" + sessionId + " has expired");
        }
        return sessionOptional.get();
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
