package com.svintsov.translator.logger;

import com.svintsov.translator.util.CachingRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = new CachingRequestWrapper((HttpServletRequest) request);
        ServletResponse responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        String requestString = IOUtils.toString(requestWrapper.getInputStream(), "UTF-8");

        MDC.put(LogbackDatabaseAppender.REQUEST, requestString);
        MDC.put(LogbackDatabaseAppender.TARGET, ((HttpServletRequest) request).getServletPath());
        MDC.put(LogbackDatabaseAppender.IP, request.getRemoteAddr());
        LOGGER.info("REQUEST: {}", requestString);

        chain.doFilter(requestWrapper, responseWrapper);

        String responseString = new String(((ContentCachingResponseWrapper) responseWrapper).getContentAsByteArray());
        ((ContentCachingResponseWrapper) responseWrapper).copyBodyToResponse();
        MDC.put(LogbackDatabaseAppender.RESPONSE, responseString);
        MDC.put(LogbackDatabaseAppender.TO_DATABASE, "TRUE");
        LOGGER.info("RESPONSE: {}", responseString);
        MDC.clear();
    }
}
