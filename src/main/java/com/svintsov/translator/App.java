package com.svintsov.translator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.svintsov.translator.service.Database;
import com.svintsov.translator.service.LoggerFilter;
import com.svintsov.translator.service.Validator;
import com.svintsov.translator.service.ValidatorFilter;
import com.svintsov.translator.util.LogbackDatabaseAppender;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(TranslatorProperties.class)
public class App implements WebMvcConfigurer {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(App.class, args);
        addCustomAppender(configurableApplicationContext, (LoggerContext) LoggerFactory.getILoggerFactory());
    }

    private static void addCustomAppender(ConfigurableApplicationContext context, LoggerContext loggerContext) {
        LogbackDatabaseAppender customAppender = context.getBean(LogbackDatabaseAppender.class);
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.addAppender(customAppender);
    }

    @Bean
    @Autowired
    public LogbackDatabaseAppender logbackCustomAppender(Database database) {
        return new LogbackDatabaseAppender(database);
    }

    @Bean
    public Validator validator() {
        return new Validator();
    }

    @Bean
    public ValidatorFilter validatorFilter() {
        return new ValidatorFilter(validator());
    }

    @Bean
    LoggerFilter loggerFilter() {
        return new LoggerFilter();
    }

    @Bean
    public FilterRegistrationBean<ValidatorFilter> validatorFilterBean(){
        FilterRegistrationBean<ValidatorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(validatorFilter());
        registrationBean.addUrlPatterns("/translate");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LoggerFilter> loggerFilterBean(){
        FilterRegistrationBean<LoggerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loggerFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    @Autowired
    public Database database(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new Database(namedParameterJdbcTemplate);
    }

}
