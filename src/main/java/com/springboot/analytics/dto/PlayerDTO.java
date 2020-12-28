package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlayerDTO {

    private String summonerName;
    private long summonerId;
    private long accountId;


}
