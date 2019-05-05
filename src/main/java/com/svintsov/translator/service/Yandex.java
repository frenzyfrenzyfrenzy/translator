package com.svintsov.translator.service;

import com.svintsov.translator.TranslatorProperties;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Yandex {

    private static final Logger LOGGER = LoggerFactory.getLogger(Yandex.class);

    private TranslatorProperties translatorProperties;
    private YandexApi yandexApi;

    public Yandex(TranslatorProperties translatorProperties) {
        this.translatorProperties = translatorProperties;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(translatorProperties.getYandexApiUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        yandexApi = retrofit.create(YandexApi.class);
    }

    public String translate(String text, String from, String to) {

        String[] words = text.trim().split("\\s");
        Flowable
                .fromArray(words)
                .flatMap(word -> yandexApi.translate(translatorProperties.getYandexApiKey(), word, String.format("%s-%s", from, to)))
                .observeOn(Schedulers.computation())
                .blockingSubscribe(
                        yandexTranslateResponse -> LOGGER.info("RESPONSE FROM FLOWABLE: {}", yandexTranslateResponse.body().getText().get(0)),
                        throwable -> throwable.printStackTrace()
                );

//        Response<YandexTranslateResponse> response;
//        try {
//            response = flowable.execute();
//        } catch (IOException e) {
//            throw new TranslatorCriticalException(e.getMessage());
//        }
//
//        if ((response.body() == null) ||
//                (response.body().getText() == null) ||
//                (response.body().getText().isEmpty()) ||
//                (response.body().getCode() != 200))
//            throw new TranslatorCriticalException("Ошибка получения ответа от API Yandex");
//        else return response.body().getText().get(0);
        return "STUB...";
    }
}
