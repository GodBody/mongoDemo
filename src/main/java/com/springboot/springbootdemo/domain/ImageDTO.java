package com.springboot.springbootdemo.domain;

public class ImageDTO {
	
	private String full;
	private String group;
	private String sprite;

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

	@Override
	public String toString() {
		return "ImageDTO [full=" + full + ", group=" + group + ", sprite=" + sprite + "]";
	}

}
