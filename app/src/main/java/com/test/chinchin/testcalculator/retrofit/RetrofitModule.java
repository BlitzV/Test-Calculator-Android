package com.test.chinchin.testcalculator.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.chinchin.testcalculator.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {


    public RetrofitModule() {
    }

    @Provides
    public EndPoints provideEndPoint() {
        return provideRetrofit(clientProvider()).create(EndPoints.class);
    }



    private Retrofit provideRetrofit(OkHttpClient client) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.ENDPOINT)
                .client(client)
                .build();

    }

    private OkHttpClient clientProvider() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.MINUTES)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();


                Headers.Builder headers = new Headers.Builder();

                headers.add("Content-Type", "application/json");

                Request request = original.newBuilder()
                        .headers(headers.build())
                        .build();

                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);




        return httpClient.build();
    }













}

