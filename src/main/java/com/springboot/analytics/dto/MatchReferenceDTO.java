package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
@Getter
@Setter
@ToString
public class MatchReferenceDTO {

    private String lane;
    @Id
    private long gameId;
    private int champion;
    private String platformId;
    private int season;
    private int queue;
    private String role;
    private long timestamp;
    private MatchDTO match;



}
