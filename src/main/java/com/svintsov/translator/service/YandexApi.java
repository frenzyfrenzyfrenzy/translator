package com.svintsov.translator.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YandexApi {

    @POST("/api/v1.5/tr.json/translate")
    Call<YandexTranslateResponse> translate(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
