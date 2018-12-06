package com.vleg.margin.entities.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceDTO {

    private Long date;
    private BigDecimal rate;
}
