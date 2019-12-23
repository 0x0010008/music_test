package com.example.music_test.control.music_control;

import com.example.music_test.control.MusicPlayException;
import com.example.music_test.control.MusicStatue;
import com.example.music_test.models.Music;

/**
 * 控制音乐播放接口
 */
public interface PlayMusic {
    /**
     * 将音乐载入内存
     * @param music
     * @throws MusicPlayException
     */
    public void loadToRam(Music music) throws MusicPlayException;

    /**
     * 停止音乐
     * @param music
     */
    public void stop(Music music) throws MusicPlayException;

    /**
     * 播放音乐
     * @param music
     */
    public void play(Music music) throws MusicPlayException;

    /**
     * 暂停音乐
     * @param music
     */
    public void pause(Music music) throws MusicPlayException;

    /**
     * 得到音乐目前的播放状态，有播放play、停止stop、暂停pause三种状态
     * @param music
     * @return
     */
    public MusicStatue getMusicStaute(Music music);

    /**
     * 跳转到音乐的指定位置
     * @param music
     * @param sec 秒
     */
    public void jumpToTime(Music music,double sec) throws MusicPlayException;

    /**
     * 得到目前的播放位置，返回值为int类型的秒数
     * @param music
     */
    public double getNowTime(Music music);

    public void destroy(Music music) throws MusicPlayException;
}
