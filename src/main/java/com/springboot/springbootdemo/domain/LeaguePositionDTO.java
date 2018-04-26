package com.springboot.springbootdemo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LeaguePositionDTO {
	private String rank;
	private String queueType;
	private int wins;
	private int losses;
	private String leagueName;
	private String playerOrTeamName;
	@Id
	private long playerOrTeamId;
	private String tier;
	private int leaguePoints;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getPlayerOrTeamName() {
		return playerOrTeamName;
	}

	public void setPlayerOrTeamName(String playerOrTeamName) {
		this.playerOrTeamName = playerOrTeamName;
	}

	public long getPlayerOrTeamId() {
		return playerOrTeamId;
	}

	public void setPlayerOrTeamId(long playerOrTeamId) {
		this.playerOrTeamId = playerOrTeamId;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public int getLeaguePoints() {
		return leaguePoints;
	}

	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
	}

	@Override
	public String toString() {
		return "LeaguePositionDTO [rank=" + rank + ", queueType=" + queueType + ", wins=" + wins + ", losses=" + losses
				+ ", leagueName=" + leagueName + ", playerOrTeamName=" + playerOrTeamName + ", playerOrTeamId="
				+ playerOrTeamId + ", tier=" + tier + ", leaguePoints=" + leaguePoints + "]";
	}

}
