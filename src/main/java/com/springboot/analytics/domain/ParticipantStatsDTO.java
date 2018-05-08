package com.springboot.analytics.domain;

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

    public long getRatio() {
        return ratio;
    }

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

    public float getKdaRatio() {
        return kdaRatio;
    }

    public int getKillInvolvement() {
        return killInvolvement;
    }

    @Override
    public String toString() {
        return "ParticipantStatsDTO{" +
                "win=" + win +
                ", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
                ", totalDamageTaken=" + totalDamageTaken +
                ", totalMinionsKilled=" + totalMinionsKilled +
                ", neutralMinionsKilled=" + neutralMinionsKilled +
                ", goldEarned=" + goldEarned +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", assists=" + assists +
                ", wardsPlaced=" + wardsPlaced +
                ", wardsKilled=" + wardsKilled +
                ", visionWardsBoughtInGame=" + visionWardsBoughtInGame +
                ", champLevel=" + champLevel +
                ", doubleKills=" + doubleKills +
                ", tripleKills=" + tripleKills +
                ", quadraKills=" + quadraKills +
                ", pentaKills=" + pentaKills +
                ", perk0=" + perk0 +
                ", perkSubStyle=" + perkSubStyle +
                ", item0=" + item0 +
                ", item1=" + item1 +
                ", item2=" + item2 +
                ", item3=" + item3 +
                ", item4=" + item4 +
                ", item5=" + item5 +
                ", item6=" + item6 +
                ", kdaRatio=" + kdaRatio +
                ", killInvolvement=" + killInvolvement +
                '}';
    }

    public void setKillInvolvement(int killCnt) {


        if (killCnt != 0) {
            float val = ((float) (this.getKills() + this.getAssists()) / (float) killCnt);
            this.killInvolvement = (int) (Math.round(val * 100));
        } else {
            this.killInvolvement = 0;
        }

    }


    public int getVisionWardsBoughtInGame() {
        return visionWardsBoughtInGame;
    }

    public void setVisionWardsBoughtInGame(int visionWardsBoughtInGame) {
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
    }

    public int getPerk0() {
        return perk0;
    }

    public void setPerk0(int perk0) {
        this.perk0 = perk0;
    }

    public int getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(int perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(int goldEarned) {
        this.goldEarned = goldEarned;
    }

    public int getWardsPlaced() {
        return wardsPlaced;
    }

    public void setWardsPlaced(int wardsPlaced) {
        this.wardsPlaced = wardsPlaced;
    }

    public int getWardsKilled() {
        return wardsKilled;
    }

    public void setWardsKilled(int wardsKilled) {
        this.wardsKilled = wardsKilled;
    }

    public int getChampLevel() {
        return champLevel;
    }

    public void setChampLevel(int champLevel) {
        this.champLevel = champLevel;
    }

    public int getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    public int getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(int pentaKills) {
        this.pentaKills = pentaKills;
    }

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public int getItem6() {
        return item6;
    }

    public void setItem6(int item6) {
        this.item6 = item6;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(int neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public int getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public void setTotalMinionsKilled(int totalMinionsKilled) {
        this.totalMinionsKilled = totalMinionsKilled;
    }

    public long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(long totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(long totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

}
