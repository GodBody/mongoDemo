package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class SummonerDTO {
    private int profileIconId;
    private String name;

    private long summonerLevel;
    private long revisionDate;

    @Id
//    @JsonProperty("_id")
    private long id;
    private long accountId;

    private Set<LeaguePositionDTO> positions;
    private List<MatchReferenceDTO> matches;




}
