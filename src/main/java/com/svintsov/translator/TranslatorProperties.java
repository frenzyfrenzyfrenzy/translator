package com.svintsov.translator;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("translator")
public class TranslatorProperties {

    private String yandexApiUrl;
    private String yandexApiKey;

    public String getYandexApiUrl() {
        return yandexApiUrl;
    }

    public void setYandexApiUrl(String yandexApiUrl) {
        this.yandexApiUrl = yandexApiUrl;
    }

    public String getYandexApiKey() {
        return yandexApiKey;
    }

    public void setYandexApiKey(String yandexApiKey) {
        this.yandexApiKey = yandexApiKey;
    }
}
