package com.svintsov.translator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("translator")
public class TranslatorProperties {

    private String yandexApiUrl;

    public String getYandexApiUrl() {
        return yandexApiUrl;
    }

    public void setYandexApiUrl(String yandexApiUrl) {
        this.yandexApiUrl = yandexApiUrl;
    }
}
