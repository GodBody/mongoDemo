package com.springboot.analytics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
public class SummonerDTO {
    private int profileIconId;
    private String name;

    private long summonerLevel;
    private long revisionDate;

    @Id
//    @JsonProperty("_id")
    private long id;
    private long accountId;

    private Set<LeaguePositionDTO> positions;
    private List<MatchReferenceDTO> matches;


    @Override
    public String toString() {
        return "SummonerDTO{" +
                "profileIconId=" + profileIconId +
                ", name='" + name + '\'' +
                ", summonerLevel=" + summonerLevel +
                ", revisionDate=" + revisionDate +
                ", id=" + id +
                ", accountId=" + accountId +
                ", positions=" + positions +
                ", matches=" + matches +
                '}';
    }

    public List<MatchReferenceDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchReferenceDTO> matches) {
        this.matches = matches;
    }

    public Set<LeaguePositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(Set<LeaguePositionDTO> positions) {
        this.positions = positions;
    }


    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public SummonerDTO(int profileIconId, String name, long summonerLevel, long revisionDate, long id, long accountId) {
        this.profileIconId = profileIconId;
        this.name = name;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.id = id;
        this.accountId = accountId;
    }

    public SummonerDTO() {
    }

}
