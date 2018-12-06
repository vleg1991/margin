package com.vleg.margin.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.BigMoney;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Component
public class PurchaseInfo {

    private LocalDate date = LocalDate.now();
    private BigMoney amount;

}
