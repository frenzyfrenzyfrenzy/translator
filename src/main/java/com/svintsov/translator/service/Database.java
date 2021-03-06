package com.svintsov.translator.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.svintsov.translator.logger.LogbackDatabaseAppender;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public Database(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeRequest(ILoggingEvent requestEvent) {
        Map<String, String> mdcPropertyMap = requestEvent.getMDCPropertyMap();

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("REQUEST", mdcPropertyMap.get(LogbackDatabaseAppender.REQUEST));
        paramsMap.put("RESPONSE", mdcPropertyMap.get(LogbackDatabaseAppender.RESPONSE));
        paramsMap.put("ERROR", mdcPropertyMap.get(LogbackDatabaseAppender.ERROR));
        paramsMap.put("TARGET", mdcPropertyMap.get(LogbackDatabaseAppender.TARGET));
        paramsMap.put("IP", mdcPropertyMap.get(LogbackDatabaseAppender.IP));

        jdbcTemplate.update("insert into REQUESTS ( REQUEST, RESPONSE, ERROR, TARGET, IP, REQUEST_TIME) values (:REQUEST, :RESPONSE, :ERROR, :TARGET, :IP, current_timestamp())", paramsMap);
    }

}
