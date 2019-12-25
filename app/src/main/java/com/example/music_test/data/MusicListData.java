package com.example.music_test.data;

import com.example.music_test.models.MusicList;

public class MusicListData {
    private static MusicList musicList;

    public static MusicList getMusicList() {
        return musicList;
    }

    public static void setMusicList(MusicList musicList) {
        MusicListData.musicList = musicList;
    }
}
