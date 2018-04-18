package com.springboot.springbootdemo.domain;

import java.util.Arrays;

public class MostChampionSummaryDTO {
	private int championId;
	private int frequency;
	private int win;
	private int losses;
	private float kdaRatio;
	private long[] dealingList;

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses() {
		this.losses = this.frequency - this.win;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public float getKdaRatio() {
		return kdaRatio;
	}

	public void setKdaRatio(float kdaRatio) {
		this.kdaRatio = kdaRatio;
	}

	public long[] getDealingList() {
		return dealingList;
	}

	public void setDealingList(long[] dealingList) {
		this.dealingList = dealingList;
	}

	@Override
	public String toString() {
		return "MostChampionSummaryDTO [championId=" + championId + ", frequency=" + frequency + ", win=" + win
				+ ", losses=" + losses + ", kdaRatio=" + kdaRatio + ", dealingList=" + Arrays.toString(dealingList)
				+ "]";
	}

}
