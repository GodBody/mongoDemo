package com.springboot.analytics.dto;

public class ParticipantIdentityDTO {
	private PlayerDTO player;
	private int participantId;

	public PlayerDTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDTO player) {
		this.player = player;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	@Override
	public String toString() {
		return "ParticipantIdentityDTO [player=" + player + ", participantId=" + participantId + ", getPlayer()="
				+ getPlayer() + ", getParticipantId()=" + getParticipantId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
