package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
@Getter
@Setter
@ToString
public class SummonerSpellListDTO {
    private Map<String, SummonerSpellDTO> data;
    private String version;
    private String type;


}
