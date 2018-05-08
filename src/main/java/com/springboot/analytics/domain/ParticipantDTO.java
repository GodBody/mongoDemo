package com.springboot.analytics.domain;

public class ParticipantDTO {
    private ParticipantStatsDTO stats;
    private int participantId;
    private int teamId;
    private String highestAchievedSeasonTier;
    private int championId;
    private String championName;





    private int spell1Id;
    private int spell2Id;

    private String spell1Name;
    private String spell2Name;

    @Override
    public String toString() {
        return "ParticipantDTO{" +
                "stats=" + stats +
                ", participantId=" + participantId +
                ", teamId=" + teamId +
                ", highestAchievedSeasonTier='" + highestAchievedSeasonTier + '\'' +
                ", championId=" + championId +
                ", championName='" + championName + '\'' +
                ", spell1Id=" + spell1Id +
                ", spell2Id=" + spell2Id +
                ", spell1Name='" + spell1Name + '\'' +
                ", spell2Name='" + spell2Name + '\'' +
                '}';
    }

    public String getSpell1Name() {
        return spell1Name;
    }

    public void setSpell1Name(String spell1Name) {
        this.spell1Name = spell1Name;
    }

    public String getSpell2Name() {
        return spell2Name;
    }

    public void setSpell2Name(String spell2Name) {
        this.spell2Name = spell2Name;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

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

}
