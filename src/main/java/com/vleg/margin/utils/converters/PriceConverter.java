package com.vleg.margin.utils.converters;

import com.vleg.margin.entities.Price;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceConverter {

    public Set<Price> convertToEntity(List<List<String>> pricesDto) {
        return pricesDto.stream()
                .map(priceDto -> {
                    if (CollectionUtils.isNotEmpty(priceDto)
                            && CollectionUtils.size(priceDto) == 2) {

                        Long dateLong = Long.parseLong(priceDto.get(0));
                        LocalDate date = Instant.ofEpochMilli(dateLong).atZone(ZoneId.systemDefault()).toLocalDate();

                        BigDecimal priceDecimal = BigDecimal.valueOf(Double.parseDouble(priceDto.get(1)));
                        BigMoney price = BigMoney.of(CurrencyUnit.of("RUB"), priceDecimal);

                        Price result = new Price();
                        result.setDate(date);
                        result.setPrice(price);

                        return result;

                    } else {
                        return null;
                    }
                })
                .collect(Collectors.toSet());
    }
}
