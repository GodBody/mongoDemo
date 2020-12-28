package com.springboot.analytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@ToString
public class MatchDTO {
    private int seasonId;
    private int queueId;
    @Id
    private long gameId;
    @JsonIgnore
    private String gameVersion;
    @JsonIgnore
    private String platformId;
    private String gameMode;
    @JsonIgnore
    private int mapId;
    @JsonIgnore
    private String gameType;
    private List<ParticipantIdentityDTO> participantIdentities;
    private List<TeamStatsDTO> teams;
    private List<ParticipantDTO> participants;
    private long gameCreation;
    private long gameDuration;

    private boolean complete;


}
