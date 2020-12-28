package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LeaguePositionDTO {
    private String rank;
    private String queueType;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean hotStreak;



}
