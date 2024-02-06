package com.pet.project.weathertracker.servlets;

import com.pet.project.weathertracker.exceptions.InvalidParameterException;
import com.pet.project.weathertracker.models.Session;
import com.pet.project.weathertracker.utils.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@Slf4j
public class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("Initializing context and TemplateEngine");
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());
        templateEngine = ThymeleafUtil.buildTemplateEngine(req.getServletContext());

        Session session = (Session) req.getAttribute("session");
        if(session != null) {
            context.setVariable("userLogin", session.getUser().getLogin());
        }

        try {
            super.service(req, resp);
        } catch (InvalidParameterException e) {
            log.warn(e.getMessage());
            context.setVariable("message", e.getMessage());
            templateEngine.process("error", context, resp.getWriter());
        } catch (Exception e) {
            log.warn(e.getMessage());
            context.setVariable("message", "Что-то пошло не так...");
            templateEngine.process("error", context, resp.getWriter());
        }
    }
}
