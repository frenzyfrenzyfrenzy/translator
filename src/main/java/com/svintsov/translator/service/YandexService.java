package com.svintsov.translator.service;

import com.svintsov.translator.TranslatorProperties;
import com.svintsov.translator.util.TranslatorCriticalException;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class YandexService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YandexService.class);

    private TranslatorProperties translatorProperties;
    private YandexApi yandexApi;

    public YandexService(TranslatorProperties translatorProperties) {
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
        String translated = Flowable
                .fromArray(words)
                .concatMap(word ->
                        yandexApi.translate(translatorProperties.getYandexApiKey(), word, String.format("%s-%s", from, to))
                                .subscribeOn(Schedulers.computation())
                                .map(yandexTranslateResponse -> {
                                    if ((yandexTranslateResponse.body() == null) ||
                                            (yandexTranslateResponse.body().getText() == null) ||
                                            (yandexTranslateResponse.body().getText().isEmpty()) ||
                                            (yandexTranslateResponse.body().getCode() != 200))
                                        throw new TranslatorCriticalException("Ошибка получения ответа от API YandexService");
                                    return yandexTranslateResponse.body().getText().get(0);
                                })
                )
                .reduce((first, second) -> first.concat(" ").concat(second))
                .blockingGet();
        LOGGER.info("TRANSLATE COMPLETED: {}", translated);
        return translated;
    }
}
