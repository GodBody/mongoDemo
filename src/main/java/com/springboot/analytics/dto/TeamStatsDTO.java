package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamStatsDTO {

    private int teamId;

    private boolean firstBlood;
    private boolean firstTower;
    private boolean firstInhibitor;
    private boolean firstRiftHerald;

    private int baronKills;
    private int dragonKills;

    private int towerKills;
    private int inhibitorKills;

    private int totalKills;
    private int totalDeaths;
    private int totalAssist;
    private int totalDamageDealtToChampions;
    private int totalDamageTaken;



}
