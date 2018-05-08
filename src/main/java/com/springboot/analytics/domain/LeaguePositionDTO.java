package com.springboot.analytics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LeaguePositionDTO {
    private String rank;
    private String queueType;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean hotStreak;

    @Override
    public String toString() {
        return "LeaguePositionDTO{" +
                "rank='" + rank + '\'' +
                ", queueType='" + queueType + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", veteran=" + veteran +
                ", hotStreak=" + hotStreak +
                ", leagueName='" + leagueName + '\'' +
                ", playerOrTeamName='" + playerOrTeamName + '\'' +
                ", freshBlood=" + freshBlood +
                ", inactive=" + inactive +
                ", leagueId='" + leagueId + '\'' +
                ", playerOrTeamId='" + playerOrTeamId + '\'' +
                ", tier='" + tier + '\'' +
                ", leaguePoints=" + leaguePoints +
                '}';
    }

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    private String leagueName;
    private String playerOrTeamName;
    private boolean freshBlood;
    private boolean inactive;
    private String leagueId;
    @Id
    private String playerOrTeamId;
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

    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    public void setPlayerOrTeamId(String playerOrTeamId) {
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

}
