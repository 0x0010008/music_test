package com.example.music_test.control.music_control;


import com.example.music_test.control.MusicPlayException;
import com.example.music_test.control.MusicStatue;
import com.example.music_test.models.Music;
import com.un4seen.bass.BASS;
public class PlayMusicImpl implements PlayMusic {
    @Override
    public void loadToRam(Music music) throws MusicPlayException {
        int res;
        res=BASS.BASS_StreamCreateFile(music.getMusicFile().getPath(),0l,0l,BASS.BASS_SAMPLE_FLOAT);
        if(res>-1&&res<11)throw new MusicPlayException("文件读取异常");
        music.setMusicHandler(res);
    }

    @Override
    public void stop(Music music) throws MusicPlayException {
        if(!BASS.BASS_ChannelStop(music.getMusicHandler()))throw new MusicPlayException("停止失败");
    }

    @Override
    public void play(Music music) throws MusicPlayException{
        if(!BASS.BASS_ChannelPlay(music.getMusicHandler(), false))throw new MusicPlayException("播放失败");
    }

    @Override
    public void pause(Music music) throws MusicPlayException {
        if(!BASS.BASS_ChannelPause(music.getMusicHandler()))throw new MusicPlayException("暂停失败");
    }

    @Override
    public MusicStatue getMusicStaute(Music music){
        MusicStatue statue;
        switch (BASS.BASS_ChannelIsActive(music.getMusicHandler()))
        {
            case 1:statue=MusicStatue.stop;break;
            case 2:statue=MusicStatue.playing;break;
            case 3:statue=MusicStatue.pause;break;
            default:statue=MusicStatue.broken;break;
        }
        return statue;
    }

    @Override
    public void jumpToTime(Music music, double sec) throws MusicPlayException {
        long bits=BASS.BASS_ChannelSeconds2Bytes(music.getMusicHandler(),sec);
        if(!BASS.BASS_ChannelSetPosition(music.getMusicHandler(),bits, BASS.BASS_POS_BYTE))throw new MusicPlayException("时间跳转失败");
    }

    @Override
    public double getNowTime(Music music) {
        long bits=BASS.BASS_ChannelGetPosition(music.getMusicHandler(), BASS.BASS_POS_BYTE);
        return BASS.BASS_ChannelBytes2Seconds(music.getMusicHandler(),bits);
    }

    @Override
    public void destroy(Music music) throws MusicPlayException {
        if(!BASS.BASS_StreamFree(music.getMusicHandler()))throw new MusicPlayException("销毁音乐内存失败");

    }
}
