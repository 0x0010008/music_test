package com.example.music_test.control.load_music;

import com.example.music_test.control.MusicPlayException;
import com.example.music_test.models.Music;
import com.un4seen.bass.BASS;

import java.io.File;

public class LoadMusicImpl implements LoadMusic {
    @Override
    public Music loadMusic(File file) {
        Music music=new Music(file);
        return music;
    }


    @Override
    public void initControler() throws MusicPlayException {
        // initialize default output device
        if (!BASS.BASS_Init(-1, 44100, 0)) {
            throw new MusicPlayException("Bass库初始化失败");
        }

        // check for floating-point capability
        if (BASS.BASS_GetConfig(BASS.BASS_CONFIG_FLOAT) == 0) {
            // no floating-point channel support
            //floatable = 0;
            // enable floating-point (8.24 fixed-point in this case) DSP/FX instead
            BASS.BASS_SetConfig(BASS.BASS_CONFIG_FLOATDSP, 1);
        }
    }
}
