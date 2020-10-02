package com.test.chinchin.testcalculator;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface EndPoints {

    @GET("get-products")
    Observable<List<Datum>> CryptoInfo();

}
