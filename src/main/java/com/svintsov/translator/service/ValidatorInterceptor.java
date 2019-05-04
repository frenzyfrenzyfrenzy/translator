package com.svintsov.translator.service;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidatorInterceptor extends HandlerInterceptorAdapter {

    private Validator validator;

    public ValidatorInterceptor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("IN INTERCEPTOR");
        return true;
    }
}
