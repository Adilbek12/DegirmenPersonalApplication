package com.degirmen.degirmenpersonalapplication.controller.service;

import com.degirmen.degirmenpersonalapplication.db.config.ConnectionConfigFromSp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonServiceGenerator {

  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));


  private static Retrofit.Builder builder =
    new Retrofit.Builder()
      .baseUrl("http://" + ConnectionConfigFromSp.getString("HOST", "10.64.0.10"))
      .addConverterFactory(GsonConverterFactory.create());

  public static <S> S createService(Class<S> serviceClass) {
    Retrofit retrofit = builder.client(httpClient.build()).build();
    return retrofit.create(serviceClass);
  }
}




