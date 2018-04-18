package com.springboot.springbootdemo.domain;

import java.util.List;

public class MatchListDTO {
	private List<MatchReferenceDTO> matches;
	private int totalGames;
	private int startIndex;
	private int endIndex;

	public List<MatchReferenceDTO> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchReferenceDTO> matches) {
		this.matches = matches;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	@Override
	public String toString() {
		return "MatchListDTO [matches=" + matches + ", totalGames=" + totalGames + ", startIndex=" + startIndex
				+ ", endIndex=" + endIndex + "]";
	}

}
