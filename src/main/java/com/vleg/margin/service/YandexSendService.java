package com.vleg.margin.service;

import com.vleg.margin.entities.dto.YandexPricesDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface YandexSendService {

    @GET("quotes/graph_1.json")
    Call<YandexPricesDTO> getPrices();
}
