package com.vleg.margin.registry;

import com.vleg.margin.entities.Price;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class PricesRegistry {

    protected Set<Price> prices;

}