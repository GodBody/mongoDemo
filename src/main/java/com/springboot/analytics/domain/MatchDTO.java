package com.springboot.analytics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class MatchDTO {
    private int seasonId;
    private int queueId;
    @Id
    private long gameId;
    @JsonIgnore
    private String gameVersion;
    @JsonIgnore
    private String platformId;
    private String gameMode;
    @JsonIgnore
    private int mapId;
    @JsonIgnore
    private String gameType;
    private List<ParticipantIdentityDTO> participantIdentities;
    private List<TeamStatsDTO> teams;
    private List<ParticipantDTO> participants;
    private long gameCreation;
    private long gameDuration;

    private boolean complete;

    @Override
    public String toString() {
        return "MatchDTO{" +
                "seasonId=" + seasonId +
                ", queueId=" + queueId +
                ", gameId=" + gameId +
                ", gameVersion='" + gameVersion + '\'' +
                ", platformId='" + platformId + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", mapId=" + mapId +
                ", gameType='" + gameType + '\'' +
                ", participantIdentities=" + participantIdentities +
                ", teams=" + teams +
                ", participants=" + participants +
                ", gameCreation=" + gameCreation +
                ", gameDuration=" + gameDuration +
                ", complete=" + complete +
                '}';
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public List<ParticipantIdentityDTO> getParticipantIdentities() {
        return participantIdentities;
    }

    public void setParticipantIdentities(List<ParticipantIdentityDTO> participantIdentities) {
        this.participantIdentities = participantIdentities;
    }

    public List<TeamStatsDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamStatsDTO> teams) {
        this.teams = teams;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

}
