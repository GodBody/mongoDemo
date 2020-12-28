package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ChampionListDTO {
    private Map<String, String> keys;
    private Map<String, ChampionDTO> data;
    private String version;
    private String type;
    private String format;


}
