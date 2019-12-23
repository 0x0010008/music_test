package com.example.music_test.control.load_music;

import com.example.music_test.control.MusicPlayException;
import com.example.music_test.models.Music;

import java.io.File;

public interface LoadMusic {

    /**
     * 初始化音乐信息
     * @param file
     * @return
     */
    public Music loadMusic(File file);


    /**
     * 初始化Bass库
     */
    public void initControler() throws MusicPlayException;
}
