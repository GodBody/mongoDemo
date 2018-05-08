package com.springboot.analytics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MatchReferenceDTO {

    private String lane;
    @Id
    private long gameId;
    private int champion;
    private String platformId;
    private int season;
    private int queue;
    private String role;
    private long timestamp;
    private MatchDTO match;

    public MatchDTO getMatch() {
        return match;
    }

    public void setMatch(MatchDTO match) {

        this.match = match;
    }

    @Override
    public String toString() {
        return "MatchReferenceDTO{" +
                "lane='" + lane + '\'' +
                ", gameId=" + gameId +
                ", champion=" + champion +
                ", platformId='" + platformId + '\'' +
                ", season=" + season +
                ", queue=" + queue +
                ", role='" + role + '\'' +
                ", timestamp=" + timestamp +
                ", match=" + match +
                '}';
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getChampion() {
        return champion;
    }

    public void setChampion(int champion) {
        this.champion = champion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
