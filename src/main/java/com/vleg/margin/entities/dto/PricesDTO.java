package com.vleg.margin.entities.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PricesDTO {

    private List<List<String>> prices = new ArrayList<>();
    private String tzoffset_hours;
    private String tzoffset_minutes;
    private String tzname;
    private List<String> legend;
}
