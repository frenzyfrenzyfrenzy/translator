package com.svintsov.translator;

import com.svintsov.translator.service.Validator;
import com.svintsov.translator.service.ValidatorFilter;
import com.svintsov.translator.service.ValidatorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(TranslatorProperties.class)
public class App implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(validatorInterceptor());
    }

    @Bean
    Validator validator() {
        return new Validator();
    }

    @Bean
    ValidatorInterceptor validatorInterceptor() {
        return new ValidatorInterceptor(validator());
    }

    @Bean
    public ValidatorFilter validatorFilter() {
        return new ValidatorFilter(validator());
    }

    @Bean
    public FilterRegistrationBean<ValidatorFilter> validatorFilterBean(){
        FilterRegistrationBean<ValidatorFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(validatorFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
