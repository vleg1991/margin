package com.vleg.margin.service;

import org.joda.money.BigMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MarginService {

    private PriceService priceService;

    @Autowired
    public MarginService(PriceService priceService) {
        this.priceService = priceService;
    }

    public BigMoney getMargin(LocalDate date, BigMoney amountUSD) {
        BigMoney sellingPrice = priceService.getSellPrice(LocalDate.now());
        BigMoney purchasePrice = priceService.getPurchasePrice(date);
        BigMoney marginPerOneDollar = sellingPrice.minus(purchasePrice.abs());
        return marginPerOneDollar.multipliedBy(amountUSD.getAmount());
    }
}
