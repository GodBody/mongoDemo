package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class MatchListDTO {
	private List<MatchReferenceDTO> matches;
	private int totalGames;
	private int startIndex;
	private int endIndex;



}
