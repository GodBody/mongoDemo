package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
public class ChampionDTO {
    @Id
    private int id;
    private String title;
    private String name;
    private int winCount;
    private int lossCount;
    private ImageDTO image;


}
