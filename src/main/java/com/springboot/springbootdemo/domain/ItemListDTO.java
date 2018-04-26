package com.springboot.springbootdemo.domain;

import java.util.Map;

public class ItemListDTO {
    private Map<String, ItemDTO> data;
    private String version;
    private String type;

    @Override
    public String toString() {
        return "ItemListDTO{" +
                "data=" + data +
                ", version='" + version + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Map<String, ItemDTO> getData() {
        return data;
    }

    public void setData(Map<String, ItemDTO> data) {
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
}
