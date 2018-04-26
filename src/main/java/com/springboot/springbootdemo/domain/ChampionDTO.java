package com.springboot.springbootdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChampionDTO {
    private String title;
    private String name;
    @Id
    private int id;

    private int winCount;
    private int lossCount;

    @Override
    public String toString() {
        return "ChampionDTO{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", winCount=" + winCount +
                ", lossCount=" + lossCount +
                ", image=" + image +
                '}';
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLossCount() {

        return lossCount;
    }

    public void setLossCount(int lossCount) {
        this.lossCount = lossCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private ImageDTO image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageDTO getImage() {
        return image;
    }

    public void setImage(ImageDTO image) {
        this.image = image;
    }

}
