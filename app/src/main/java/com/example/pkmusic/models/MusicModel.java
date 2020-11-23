package com.example.pkmusic.models;

import io.realm.RealmObject;

public class MusicModel extends RealmObject {

    private String musicId;
    private String name;
    private String poster;
    private String path;
    private String author;


    public String getMusicId() {
        return musicId;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getPoster() {
        return poster;
    }

    public String getAuthor() {
        return author;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
