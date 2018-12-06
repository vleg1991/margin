package com.vleg.margin.controller;

import com.vleg.margin.exceptions.InvalidInputException;
import com.vleg.margin.service.MarginService;
import com.vleg.margin.view.PurchaseInfo;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

@Controller
public class MarginController {

    private MarginService marginService;

    @Autowired
    public MarginController(MarginService marginService) {
        this.marginService = marginService;
    }

    public BigMoney getMargin(@Valid PurchaseInfo purchaseInfo) throws InvalidInputException {
        if (Objects.isNull(purchaseInfo)) {
            throw new InvalidInputException("Input is null");
        } else {
            if (Objects.isNull(purchaseInfo.getDate()))
                throw new InvalidInputException("Date is null");
            if (purchaseInfo.getDate().isAfter(LocalDate.now()))
                throw new InvalidInputException("Date is after now");
            if (Objects.isNull(purchaseInfo.getAmount()))
                throw new InvalidInputException("Amount is null");
            if (!CurrencyUnit.USD.equals(purchaseInfo.getAmount().getCurrencyUnit()))
                throw new InvalidInputException("Amount is not USD");
        }

        BigMoney marginValuePerOneDollar = marginService.getMarginBetweenNowAndSpecifiedDate(purchaseInfo.getDate());
        return marginService.getMarginAmount(purchaseInfo.getAmount(), marginValuePerOneDollar);
    }
}
