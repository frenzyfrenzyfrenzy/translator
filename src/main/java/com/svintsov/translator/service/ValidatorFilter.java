package com.svintsov.translator.service;

import javax.servlet.*;
import java.io.IOException;

public class ValidatorFilter implements Filter {

    private Validator validator;

    public ValidatorFilter(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("IN FILTER");
    }

}
