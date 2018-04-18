package com.springboot.springbootdemo.domain;

public class ParticipantDTO {
	private ParticipantStatsDTO stats;
	private int participantId;
	private int teamId;
	private String highestAchievedSeasonTier;
	private int championId;
	private int spell1Id;
	private int spell2Id;

	public ParticipantStatsDTO getStats() {
		return stats;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getSpell1Id() {
		return spell1Id;
	}

	public void setSpell1Id(int spell1Id) {
		this.spell1Id = spell1Id;
	}

	public int getSpell2Id() {
		return spell2Id;
	}

	public void setSpell2Id(int spell2Id) {
		this.spell2Id = spell2Id;
	}

	public void setStats(ParticipantStatsDTO stats) {
		this.stats = stats;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public String getHighestAchievedSeasonTier() {
		return highestAchievedSeasonTier;
	}

	public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
		this.highestAchievedSeasonTier = highestAchievedSeasonTier;
	}

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParticipantDTO [stats=" + stats + ", participantId=" + participantId + ", teamId=" + teamId
				+ ", highestAchievedSeasonTier=" + highestAchievedSeasonTier + ", championId=" + championId
				+ ", spell1Id=" + spell1Id + ", spell2Id=" + spell2Id + "]";
	}

}
