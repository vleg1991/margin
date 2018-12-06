package com.vleg.margin.service;

import org.joda.money.BigMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MarginService {

    private PriceService priceService;

    @Autowired
    public MarginService(PriceService priceService) {
        this.priceService = priceService;
    }

    public BigMoney getMarginBetweenNowAndSpecifiedDate(LocalDate date) {
        BigMoney todayMargin = getMargin(LocalDate.now());
        BigMoney requiredMargin = getMargin(date);
        return todayMargin.minus(requiredMargin);
    }

    public BigMoney getMargin(LocalDate date) {
        BigMoney purchasePrice = priceService.getPurchasePrice(date);
        BigMoney sellingPrice = priceService.getSellPrice(date);
        return sellingPrice.minus(purchasePrice);
    }

    public BigMoney getMarginAmount(BigMoney amount, BigMoney margin) {
        BigDecimal amountPurchase = amount.getAmount();
        return margin.multipliedBy(amountPurchase);
    }
}
