package com.vleg.margin.service;

import com.vleg.margin.entities.dto.PricesDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface YandexSendService {

    @GET("quotes/graph_1.json")
    Call<PricesDTO> getPrices();
}
