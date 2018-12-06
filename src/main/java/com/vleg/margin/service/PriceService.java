package com.vleg.margin.service;

import com.vleg.margin.entities.Price;
import com.vleg.margin.exceptions.PricesRetrievingException;
import com.vleg.margin.registry.PricesRegistry;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.money.BigMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceService extends PricesRegistry {

    private BigDecimal spreadPercentage;
    private YandexService yandexService;

    @Autowired
    public PriceService( @Value("${magrin.spread.percents}") String spreadPercentage,
                         YandexService yandexService ) {
        super();
        this.spreadPercentage = BigDecimal.valueOf(Double.parseDouble(spreadPercentage));
        this.yandexService = yandexService;
    }

    public BigMoney getPurchasePrice(LocalDate purchaseDate) {

        BigMoney purchasePrice = getPriceBy(purchaseDate).getPrice();
        BigDecimal spreadAmount = getSpread(purchasePrice);

        return purchasePrice.plus(spreadAmount);
    }

    public BigMoney getSellPrice(LocalDate sellDate) {

        BigMoney sellPrice = getPriceBy(sellDate).getPrice();
        BigDecimal spreadAmount = getSpread(sellPrice);

        return sellPrice.minus(spreadAmount);
    }

    private BigDecimal getSpread(BigMoney price) {
        BigDecimal purchaseAmount = price.getAmount();
        return purchaseAmount
                .divide(BigDecimal.valueOf(100))
                .multiply(spreadPercentage);
    }

    public Price getPriceBy(LocalDate date) throws PricesRetrievingException {
        Price result = null;
        if (CollectionUtils.isNotEmpty(prices) && Objects.nonNull(date)) {
            result = getPriceFromRegistry(date);
        }
        if (Objects.isNull(result)) {
            prices = yandexService.retrievePrices();
            result = getPriceFromRegistry(date);
            if (Objects.isNull(result)) {
                throw new PricesRetrievingException("Yandex response did not provide data for this credentials");
            }
        }
        return result;
    }

    public Price getPriceFromRegistry(LocalDate date) {

        Set<Price> result = prices.stream().filter(price ->
             date.equals(price.getDate())
                     || date.minusDays(1).equals(price.getDate())
                     || date.minusDays(2).equals(price.getDate())
         ).collect(Collectors.toSet());

        return result.stream().reduce((price, price2) -> {
            if (price.getDate().isAfter(price2.getDate()))
                return price;
            else
                return price2;
        }).orElse(null);
    }
}
