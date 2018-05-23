package com.springboot.analytics.dto;

import java.util.Map;

public class ChampionListDTO {
    private Map<String, String> keys;
    private Map<String, ChampionDTO> data;
    private String version;
    private String type;
    private String format;

    @Override
    public String toString() {
        return "ChampionListDTO{" +
                "keys=" + keys +
                ", data=" + data +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    public Map<String, ChampionDTO> getData() {
        return data;
    }

    public void setData(Map<String, ChampionDTO> data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
