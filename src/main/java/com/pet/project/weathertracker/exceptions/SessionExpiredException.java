package com.pet.project.weathertracker.exceptions;

import jakarta.servlet.ServletException;

public class SessionExpiredException extends ServletException {
    public SessionExpiredException(String msg) {
        super(msg);
    }
}
