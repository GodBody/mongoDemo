package com.springboot.analytics.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MostChampion {

    private String championName;
    private int kill;
    private int death;
    private int assist;

    private int gameCount;

    private int winCount;
    private int looseCount;

    private float kdaRatio;

    private int winRate;


    public void setWinRate() {
        float val = (float) ((float) this.winCount / (float) this.gameCount);
        this.winRate = (int) (val * 100);
    }

    public void setKdaRatio() {
        if (this.getDeath() != 0) {
            float val = (float) (this.getKill() + this.getAssist()) / this.getDeath();
            this.kdaRatio = (float) (Math.round(val * 100) / 100.0);
        } else {
            float val = (float) ((this.getKill() + this.getAssist()) * 1.2);
            this.kdaRatio = (float) (Math.round(val * 100) / 100.0);
        }
    }

    public void addKill(int kill) {
        this.kill += kill;
    }

    public void addDeath(int death) {
        this.death += death;
    }

    public void addAssist(int assist) {
        this.assist += assist;
    }

    public void addGameCount() {
        this.gameCount++;
    }

    public void addWinCount() {
        this.winCount++;
    }

    public void setLooseCount() {
        this.looseCount = this.gameCount - this.winCount;
    }


}
