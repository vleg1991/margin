package com.vleg.margin.service;

import com.vleg.margin.entities.Price;
import com.vleg.margin.utils.PriceBuilder;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceTest {

    @Mock
    private YandexService yandexService;

    @InjectMocks
    private PriceService priceService = spy(new PriceService("0.5", yandexService));

    private Answer<Set<Price>> yandexServiceAnswer = invocationOnMock -> {
        Set<Price> prices = new HashSet<>();
        prices.add(
                PriceBuilder.builder()
                        .date(LocalDate.now())
                        .price(BigMoney.of(CurrencyUnit.of("RUB"), 66.7377))
                        .build()
        );
        prices.add(
                PriceBuilder.builder()
                        .date(LocalDate.now().minusYears(8))
                        .price(BigMoney.of(CurrencyUnit.of("RUB"), 30.0001))
                        .build()
        );
        prices.add(
                PriceBuilder.builder()
                        .date(LocalDate.now().minusYears(18))
                        .price(BigMoney.of(CurrencyUnit.of("RUB"), 10.0000))
                        .build()
        );
        return prices;
    };

    @Before
    public void setUp() {
        when(yandexService.retrievePrices()).thenAnswer(yandexServiceAnswer);
    }

    @Test
    public void getPurchasePrice() throws Exception {
        Double expectedMargin = 66.7377 / 100 * 0.5;
        BigMoney expectedResult = BigMoney.of(CurrencyUnit.of("RUB"), 66.7377 + expectedMargin);
        assertEquals(priceService.getPurchasePrice(LocalDate.now()), expectedResult);
        verify(priceService).getPurchasePrice(LocalDate.now());
    }

    @Test
    public void getSellPrice() throws Exception {
        BigDecimal expectedMargin = BigDecimal.valueOf(new Double(66.7377 / 100 * 0.5));
        BigMoney expectedResult = BigMoney.of(CurrencyUnit.of("RUB"), BigDecimal.valueOf(66.7377).subtract(expectedMargin));

        BigMoney result = priceService.getSellPrice(LocalDate.now());

        assertEquals(result.rounded(2, RoundingMode.DOWN), expectedResult.rounded(2, RoundingMode.DOWN));
        verify(priceService).getSellPrice(LocalDate.now());
    }

    @Test
    public void getPriceBy() throws Exception {
        Price expectedResult = PriceBuilder.builder()
                .date(LocalDate.now())
                .price(BigMoney.of(CurrencyUnit.of("RUB"), 66.7377))
                .build();
        assertEquals(priceService.getPriceBy(LocalDate.now()), expectedResult);
        verify(priceService).getPriceFromRegistry(LocalDate.now());
        verify(yandexService).retrievePrices();
        verify(priceService, times(1)).getPriceFromRegistry(LocalDate.now());

    }

    @Test
    public void getPriceFromRegistry() throws Exception {
//        BigMoney expectedResult = BigMoney.of(CurrencyUnit.of("RUB"), 66.7377);
//        assertEquals(priceService.getSellPrice(LocalDate.now()), expectedResult);
//        verify(priceService).getSellPrice(LocalDate.now());
    }

}