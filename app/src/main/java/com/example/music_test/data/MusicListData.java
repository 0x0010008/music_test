package com.example.music_test.data;

import com.example.music_test.models.MusicListCursor;

public class MusicListData {
    private static MusicListCursor musicList;

    public static MusicListCursor getMusicList() {
        return musicList;
    }

    public static void setMusicList(MusicListCursor musicList) {
        MusicListData.musicList = musicList;
    }
}
