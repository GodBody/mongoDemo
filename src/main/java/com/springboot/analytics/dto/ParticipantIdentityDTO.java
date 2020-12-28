package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipantIdentityDTO {
    private PlayerDTO player;
    private int participantId;


}
