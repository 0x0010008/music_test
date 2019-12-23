package com.example.music_test.models;

import android.media.Image;

public class MusicInfo {
    private String author;//作者
    private String album;//专辑
    private int length;//曲长
    private Image image;//专辑封面

    public MusicInfo(String author, String album, int length, Image image) {
        this.author = author;
        this.album = album;
        this.length = length;
        this.image = image;
    }

    public MusicInfo() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
