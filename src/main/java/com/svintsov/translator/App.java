package com.svintsov.translator;

import com.svintsov.translator.service.LoggerFilter;
import com.svintsov.translator.service.Validator;
import com.svintsov.translator.service.ValidatorFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@SpringBootApplication
@EnableConfigurationProperties(TranslatorProperties.class)
public class App implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
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
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        dataSourceBuilder.url("jdbc:h2:~/test");
        dataSourceBuilder.type(HikariDataSource.class);
        return dataSourceBuilder.build();
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
}
