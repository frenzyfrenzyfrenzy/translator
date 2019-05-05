package com.svintsov.translator.service;

import com.svintsov.translator.util.CachingRequestWrapper;
import com.svintsov.translator.util.JsonUtils;
import com.svintsov.translator.util.LogbackDatabaseAppender;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ValidatorFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorFilter.class);

    private Validator validator;

    public ValidatorFilter(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = new CachingRequestWrapper((HttpServletRequest) request);
        String requestString = IOUtils.toString(requestWrapper.getInputStream(), "UTF-8");

        List<String> validationErrors = validator.validateTranslateRequest(requestString);
        if (validationErrors == null)
            chain.doFilter(requestWrapper, response);
        else {
            String validationErrorsString = JsonUtils.silentToJsonString(validationErrors);
            MDC.put(LogbackDatabaseAppender.ERROR, validationErrorsString);
            LOGGER.error("VALIDATION ERRORS: {}", validationErrorsString);
            ((HttpServletResponse)response).sendError(400, JsonUtils.silentToJsonString(validationErrors));
        }
    }

}
