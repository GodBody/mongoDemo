package com.springboot.analytics.dto;

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

	@Override
	public String toString() {
		return "TeamStatsDTO{" +
				"teamId=" + teamId +
				", firstBlood=" + firstBlood +
				", firstTower=" + firstTower +
				", firstInhibitor=" + firstInhibitor +
				", firstRiftHerald=" + firstRiftHerald +
				", baronKills=" + baronKills +
				", dragonKills=" + dragonKills +
				", towerKills=" + towerKills +
				", inhibitorKills=" + inhibitorKills +
				", totalKills=" + totalKills +
				", totalDeaths=" + totalDeaths +
				", totalAssist=" + totalAssist +
				", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
				", totalDamageTaken=" + totalDamageTaken +
				'}';
	}

	public int getTotalDamageTaken() {
		return totalDamageTaken;
	}

	public void setTotalDamageTaken(int totalDamageTaken) {
		this.totalDamageTaken = totalDamageTaken;
	}

	public int getTotalDamageDealtToChampions() {
		return totalDamageDealtToChampions;
	}

	public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {
		this.totalDamageDealtToChampions = totalDamageDealtToChampions;
	}

	public int getTotalKills() {
		return totalKills;
	}

	public void setTotalKills(int totalKills) {
		this.totalKills = totalKills;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalAssist() {
		return totalAssist;
	}

	public void setTotalAssist(int totalAssist) {
		this.totalAssist = totalAssist;
	}

	public boolean isFirstInhibitor() {
		return firstInhibitor;
	}

	public void setFirstInhibitor(boolean firstInhibitor) {
		this.firstInhibitor = firstInhibitor;
	}

	public boolean isFirstRiftHerald() {
		return firstRiftHerald;
	}

	public void setFirstRiftHerald(boolean firstRiftHerald) {
		this.firstRiftHerald = firstRiftHerald;
	}

	public int getInhibitorKills() {
		return inhibitorKills;
	}

	public void setInhibitorKills(int inhibitorKills) {
		this.inhibitorKills = inhibitorKills;
	}

	public int getBaronKills() {
		return baronKills;
	}

	public void setBaronKills(int baronKills) {
		this.baronKills = baronKills;
	}

	public int getDragonKills() {
		return dragonKills;
	}

	public void setDragonKills(int dragonKills) {
		this.dragonKills = dragonKills;
	}

	public int getTowerKills() {
		return towerKills;
	}

	public void setTowerKills(int towerKills) {
		this.towerKills = towerKills;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public boolean isFirstBlood() {
		return firstBlood;
	}

	public void setFirstBlood(boolean firstBlood) {
		this.firstBlood = firstBlood;
	}

	public boolean isFirstTower() {
		return firstTower;
	}

	public void setFirstTower(boolean firstTower) {
		this.firstTower = firstTower;
	}

}
