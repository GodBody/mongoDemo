package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
@Getter
@Setter
@ToString
public class ItemListDTO {
    private Map<String, ItemDTO> data;
    private String version;
    private String type;


}
