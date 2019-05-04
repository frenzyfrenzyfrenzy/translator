package com.svintsov.translator.service;

import com.svintsov.translator.util.CachingRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = new CachingRequestWrapper((HttpServletRequest) request);
        String requestString = IOUtils.toString(requestWrapper.getInputStream(), "UTF-8");

        LOGGER.info("REQUEST: {}", requestString);
        chain.doFilter(requestWrapper, response);
    }
}
