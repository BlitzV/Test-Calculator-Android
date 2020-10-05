package com.test.chinchin.testcalculator.retrofit;

import com.test.chinchin.testcalculator.models.ApiModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EndPoints {

    @GET("get-products")
    Observable<ApiModel> CryptoInfo();

}
