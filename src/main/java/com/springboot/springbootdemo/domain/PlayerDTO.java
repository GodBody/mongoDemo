package com.springboot.springbootdemo.domain;

public class PlayerDTO {
	
	private String summonerName;
	private long summonerId;
	private long accountId;

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public long getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(long summonerId) {
		this.summonerId = summonerId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "PlayerDTO [summonerName=" + summonerName + ", summonerId=" + summonerId + ", accountId=" + accountId
				+ "]";
	}

}
