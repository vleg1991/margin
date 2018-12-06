package com.vleg.margin.service;

import com.vleg.margin.entities.Price;
import com.vleg.margin.entities.dto.YandexPricesDTO;
import com.vleg.margin.exceptions.PricesRetrievingException;
import com.vleg.margin.utils.converters.PriceConverter;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class YandexService {

    private static final Logger logger = LoggerFactory.getLogger(YandexService.class);

    private String yandexUri;
    private PriceConverter priceConverter;
    private YandexSendService yandexSendService;

    @Autowired
    public YandexService( @Value("${margin.yandex.uri}") String yandexUri,
                          PriceConverter priceConverter ) {
        this.yandexUri = yandexUri;
        this.priceConverter = priceConverter;
        this.yandexSendService = createYandexSendService();
    }

    public Set<Price> retrievePrices() {

        YandexPricesDTO result = null;

        try {
            Response<YandexPricesDTO> response = yandexSendService.getPrices().execute();
            if (response.isSuccessful()) {
                result = response.body();
            } else {
                logger.error("Response isn't successful: {}", response.raw());
            }
        } catch (IOException e) {
            logger.error("Parsing or anything else goes wrong", e);
            throw new PricesRetrievingException("Parsing or anything else goes wrong");
        }

        return Objects.isNull(result) ? null : priceConverter.convertToEntity(result.getPrices());
    }

    private YandexSendService createYandexSendService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(String.format("https://%s", yandexUri))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(YandexSendService.class);
    }
}
