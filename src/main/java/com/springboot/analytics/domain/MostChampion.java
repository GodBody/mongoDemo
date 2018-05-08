package com.springboot.analytics.domain;

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

    public float getWinRate() {
        return winRate;
    }

    @Override
    public String toString() {
        return "MostChampion{" +
                "championName='" + championName + '\'' +
                ", kill=" + kill +
                ", death=" + death +
                ", assist=" + assist +
                ", gameCount=" + gameCount +
                ", winCount=" + winCount +
                ", looseCount=" + looseCount +
                ", kdaRatio=" + kdaRatio +
                ", winRate=" + winRate + "%" +
                '}';
    }

    public void setWinRate() {
        float val = (float) ((float) this.winCount / (float) this.gameCount);
        this.winRate = (int) (val * 100);
    }

    public float getKdaRatio() {
        return kdaRatio;
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

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public void setLooseCount() {
        this.looseCount = this.gameCount - this.winCount;
    }

    public int getLooseCount() {
        return looseCount;
    }

}
