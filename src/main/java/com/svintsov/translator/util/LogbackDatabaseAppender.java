package com.svintsov.translator.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.svintsov.translator.service.Database;
import org.springframework.context.SmartLifecycle;

public class LogbackDatabaseAppender extends UnsynchronizedAppenderBase<ILoggingEvent> implements SmartLifecycle {

    private Database database;

    public static final String TO_DATABASE = "TO_DATABASE";

    public static final String REQUEST = "REQUEST";
    public static final String RESPONSE = "RESPONSE";
    public static final String ERROR = "ERROR";
    public static final String TARGET = "TARGET";
    public static final String IP = "IP";

    public LogbackDatabaseAppender(Database database) {
        this.database = database;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if ("TRUE".equals(event.getMDCPropertyMap().get(TO_DATABASE)))
            database.writeRequest(event);
    }

    @Override
    public boolean isRunning() {
        return isStarted();
    }

}
