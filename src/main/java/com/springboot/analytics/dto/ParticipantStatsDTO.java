package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParticipantStatsDTO {

    private boolean win;
    private long totalDamageDealtToChampions;
    private long totalDamageTaken;
    private int totalMinionsKilled;
    private int neutralMinionsKilled;
    private int goldEarned;
    private int kills;
    private int deaths;
    private int assists;
    private int wardsPlaced;
    private int wardsKilled;
    private int visionWardsBoughtInGame;
    private int champLevel;
    private int doubleKills;
    private int tripleKills;
    private int quadraKills;
    private int pentaKills;
    private int perk0;
    private int perkSubStyle;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private float kdaRatio;
    private int killInvolvement;
    private long ratio;


    public void setRatio(long maxDeal) {
        this.ratio = (long) ((float) this.totalDamageDealtToChampions / maxDeal * 100);
    }

    public void setKdaRatio() {

        if (this.getDeaths() != 0) {
            float val = (float) (this.getKills() + this.getAssists()) / this.getDeaths();
            this.kdaRatio = (float) (Math.round(val * 100) / 100.0);
        } else {
            float val = (float) ((this.getKills() + this.getAssists()) * 1.2);
            this.kdaRatio = (float) (Math.round(val * 100) / 100.0);
        }
    }


    public void setKillInvolvement(int killCnt) {
        if (killCnt != 0) {
            float val = ((float) (this.getKills() + this.getAssists()) / (float) killCnt);
            this.killInvolvement = (int) (Math.round(val * 100));
        } else {
            this.killInvolvement = 0;
        }

    }


    public void setVisionWardsBoughtInGame(int visionWardsBoughtInGame) {
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
    }


}