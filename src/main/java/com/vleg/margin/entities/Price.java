package com.vleg.margin.entities;

import lombok.*;
import org.joda.money.BigMoney;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private LocalDate date;
    private BigMoney price;
}
