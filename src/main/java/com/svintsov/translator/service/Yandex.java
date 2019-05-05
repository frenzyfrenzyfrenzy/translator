package com.svintsov.translator.service;

import com.svintsov.translator.TranslatorProperties;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class Yandex {

    private TranslatorProperties translatorProperties;
    private YandexApi yandexApi;

    public Yandex(TranslatorProperties translatorProperties) {
        this.translatorProperties = translatorProperties;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(translatorProperties.getYandexApiUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        yandexApi = retrofit.create(YandexApi.class);
    }

    public String translate(String text, String from, String to) {
        Call<YandexTranslateResponse> call =
                yandexApi.translate(translatorProperties.getYandexApiKey(), text, String.format("%s-%s", from, to));

        Response<YandexTranslateResponse> response;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response.body().getText().get(0);
    }
}
