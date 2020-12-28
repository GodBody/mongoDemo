package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@ToString
public class ItemDTO {
    @Id
    private int id;
    private String plaintext;
    private String description;
    private ImageDTO image;


}
