package com.example.music_test;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_test.control.MusicControler;
import com.example.music_test.control.MusicPlayException;
import com.example.music_test.control.load_music.LoadMusic;
import com.example.music_test.control.load_music.LoadMusicImpl;
import com.example.music_test.control.music_control.PlayMusic;
import com.example.music_test.control.music_control.PlayMusicImpl;
import com.example.music_test.control.music_control.PlayToEnd;
import com.example.music_test.data.MusicListData;
import com.example.music_test.models.LoopStatue;
import com.example.music_test.models.Music;
import com.example.music_test.models.MusicListCursorImpl;
import com.un4seen.bass.BASS;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LoadMusic loadMusic;
    PlayMusic playMusic;
    Music music;
    TextView tv;
    EditText editText;
    ImageView iv;

    File filepath;
    String[] filelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        tv=findViewById(R.id.textView);
        editText=findViewById(R.id.editText);
        filepath = Environment.getExternalStorageDirectory();
        loadMusic=new LoadMusicImpl();
        playMusic=new PlayMusicImpl();
        iv=findViewById(R.id.imageView);
        List<Music> musicList=new ArrayList<>();
        try {
            musicList.add(loadMusic.musicFactory(new File("/storage/emulated/0/Music/Gravity.mp3")));
            musicList.add(loadMusic.musicFactory(new File("/storage/emulated/0/Music/Symphony No. 9 - Iv. Allegro Con Fuoco.mp3")));
            musicList.add(loadMusic.musicFactory(new File("/storage/emulated/0/Music/80. Hotel California (Live in Santa Monica, 7-29-1980) [Remastered].flac")));
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
        MusicListData.setMusicList(new MusicListCursorImpl(musicList));

        try{
            loadMusic.initControler();
        }catch (MusicPlayException e)
        {
            makeError(e.getMessage());
        }
    }




    public void OpenClicked(View v) {
        String[] list = filepath.list();
        if (list == null) list = new String[0];
        if (!filepath.getPath().equals("/")) {
            filelist = new String[list.length + 1];
            filelist[0] = "..";
            System.arraycopy(list, 0, filelist, 1, list.length);
        } else
            filelist = list;
        Arrays.sort(filelist, String.CASE_INSENSITIVE_ORDER);
        new AlertDialog.Builder(this)
                .setTitle("Choose a file to play")
                .setItems(filelist, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        File sel;
                        if (filelist[which].equals("..")) sel = filepath.getParentFile();
                        else sel = new File(filepath, filelist[which]);
                        if (sel.isDirectory()) {
                            filepath = sel;
                            OpenClicked(null);
                        } else {
                            //String file = sel.getPath();
//                            // first free the current one (try both MOD and stream - it must be one of them)
//                            BASS.BASS_MusicFree(chan);
//                            BASS.BASS_StreamFree(chan);
//                            if ((chan = BASS.BASS_StreamCreateFile(file, 0, 0, BASS.BASS_SAMPLE_LOOP | floatable)) == 0
//                                    && (chan = BASS.BASS_MusicLoad(file, 0, 0, BASS.BASS_SAMPLE_LOOP | BASS.BASS_MUSIC_RAMP | floatable, 1)) == 0) {
//                                // whatever it is, it ain't playable
//                                ((Button) findViewById(R.id.button)).setText("press here to open a file");
//                                Error("Can't play the file");
//                                return;
//                            }
//                            ((Button) findViewById(R.id.button)).setText(file);
//                            //BASS.BASS_ChannelPlay(chan, false);
                            try {
                                music=loadMusic.musicFactory(sel);
                                List<Music> musicList=new ArrayList<>();
                                musicList.add(music);
                                MusicListData.setMusicList(new MusicListCursorImpl(musicList));
                                tv.setText(music.getMusicFile().getName());
                                if(music.getMusicInfo().getImage()!=null)iv.setImageBitmap(music.getMusicInfo().getImage());
                            }
                            catch (MusicPlayException e)
                            {
                                makeError(e.getMessage());
                            }
                        }
                    }
                })
                .show();
    }

    // display error messages
    void makeError(String es) {
        // get error code in current thread for display in UI thread
        String s = String.format("%s\n(error code: %d)", es, BASS.BASS_ErrorGetCode());

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void pauseClick(View view) {
        try {
            MusicControler.pause();
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void playClick(View view) {
        try {
            PlayToEnd playToEnd=new PlayToEnd() {
                @Override
                public void playToEndFunc() {
                }
            };
            MusicControler.setCallBack(playToEnd);
            MusicControler.play();
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void stopClick(View view) {
        try {
            MusicControler.stop();
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void getPositionClick(View view) {
        try {
            double res=MusicControler.getNowPos();
            editText.setText(String.valueOf(res));
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void setPositionClick(View view) {
        try {
            MusicControler.jumpToPos(Double.valueOf(editText.getText().toString()));
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void btnStatueClick(View view) {
        try {
            editText.setText(MusicControler.getMusicStatue()+"");
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void singleClick(View view) {
        MusicListData.getMusicList().setLoopStatue(LoopStatue.singleloop);
    }

    public void loopClick(View view) {
        MusicListData.getMusicList().setLoopStatue(LoopStatue.loop);
    }

    public void unloopClick(View view) {
        MusicListData.getMusicList().setLoopStatue(LoopStatue.unloop);
    }

    public void randomLoopClick(View view) {
        MusicListData.getMusicList().setLoopStatue(LoopStatue.looprandom);
    }

    public void nextClick(View view) {
        try {
            MusicControler.playNext();
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }

    public void previousClick(View view) {
        try {
            MusicControler.playPrevious();
        } catch (MusicPlayException e) {
            makeError(e.getMessage());
        }
    }
}
