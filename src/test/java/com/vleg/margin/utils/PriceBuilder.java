package com.vleg.margin.utils;

import com.vleg.margin.entities.Price;
import org.joda.money.BigMoney;

import java.time.LocalDate;

public class PriceBuilder {

    private LocalDate date;
    private BigMoney price;

    public static PriceBuilder builder() {
        return new PriceBuilder();
    }

    public PriceBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public PriceBuilder price(BigMoney price) {
        this.price = price;
        return this;
    }

    public Price build() {
        return new Price(date, price);
    }


}
