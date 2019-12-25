package com.example.music_test.control;

import com.example.music_test.control.music_control.PlayMusic;
import com.example.music_test.control.music_control.PlayMusicImpl;
import com.example.music_test.control.music_control.PlayToEnd;
import com.example.music_test.data.MusicListData;
import com.example.music_test.models.Music;
import com.example.music_test.models.MusicList;
import com.un4seen.bass.BASS;

public class MusicControler {
    private static PlayMusic playMusic=new PlayMusicImpl();
    private static PlayToEnd playToEnd;

    public static void setCallBack(PlayToEnd callBack)
    {
        playToEnd=callBack;
    }

    private static Music getNowMusic()
    {
        return MusicListData.getMusicList().getNowMusic();
    }

    /**
     * 播放列表中nowPos对应的音乐
     * @throws MusicPlayException
     */
    public static void play() throws MusicPlayException
    {
        if(BASS.BASS_IsStarted())
        {
            if(getMusicStatue()!=MusicStatue.stop)
            {
                stop();
                playMusic.chear();
            }
        }
        playMusic.loadToRam(getNowMusic());
        playMusic.play(getNowMusic(),playToEnd);
    }

    /**
     * 暂停列表中nowPos对应的音乐
     * @throws MusicPlayException
     */
    public static void pause() throws MusicPlayException
    {
        playMusic.pause(getNowMusic());
    }

    /**
     * 停止列表中nowPos对应的音乐
     * @throws MusicPlayException
     */
    public static void stop() throws MusicPlayException
    {
        playMusic.stop(getNowMusic());
    }

    /**
     * 得到播放列表中nowPos对应音乐的播放状态
     * @return
     * @throws MusicPlayException
     */
    public static MusicStatue getMusicStatue() throws MusicPlayException
    {
        return playMusic.getMusicStaute(getNowMusic());
    }

    /**
     * 跳转列表中nowPos对应音乐到指定时间
     * @param sec
     * @throws MusicPlayException
     */
    public static void jumpToPos(double sec) throws MusicPlayException
    {
        playMusic.jumpToTime(getNowMusic(),sec);
    }

    /**
     * 得到列表中nowPos对应音乐目前播放的位置
     * @return
     * @throws MusicPlayException
     */
    public static double getNowPos() throws MusicPlayException
    {
        return playMusic.getNowTime(getNowMusic());
    }

    /**
     * 清空目前所有存在在Ram中的音乐
     * @throws MusicPlayException
     */
    public static void clear() throws MusicPlayException
    {
        playMusic.chear();
    }

    /**
     * 播放下一首音乐
     * @throws MusicPlayException
     */
    public static void playNext() throws MusicPlayException
    {
        if(MusicListData.getMusicList().moveToNext()) play();
    }

    /**
     * 播放上一首音乐
     * @throws MusicPlayException
     */
    public static void playPrevious() throws MusicPlayException
    {
        if(MusicListData.getMusicList().moveToPrevious()) play();
    }

    /**
     * 播放指定索引位置的音乐
     * @param pos
     * @throws MusicPlayException
     */
    public static void playTarget(int pos) throws MusicPlayException
    {
        MusicListData.getMusicList().setNowPos(pos);
        play();
    }
}
