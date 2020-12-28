package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipantDTO {
    private ParticipantStatsDTO stats;
    private int participantId;
    private int teamId;
    private String highestAchievedSeasonTier;
    private int championId;
    private String championName;





    private int spell1Id;
    private int spell2Id;

    private String spell1Name;
    private String spell2Name;



}
