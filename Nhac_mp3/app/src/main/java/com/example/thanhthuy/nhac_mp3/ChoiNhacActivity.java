package com.example.thanhthuy.nhac_mp3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.LayMusic.SongMusic;
import com.example.thanhthuy.nhac_mp3.LayMusic.Songs;
import com.example.thanhthuy.nhac_mp3.imger.ShadowImageView;
import com.google.gson.Gson;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.example.thanhthuy.nhac_mp3.R.id.seekBar;

public class ChoiNhacActivity extends AppCompatActivity {

    ImageButton ibPlay, ibNext, ibPrevious, ibLapLai, ibNgauNhien, ibStop;
    SeekBar seekBarSong;
    TextView textViewStart, textViewEnd, tvTenBaiHat;
    private MediaPlayer mp;
    private SongMusic[] arrSong;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    private ImageView mBackgroundImage;
    private int currentSongIndex = 0;
    int position;
    Intent intent;
    Random r;
    private boolean isRepeat = false;
    private boolean isShuffle = false;
    SongMusic itemPlay;
    private android.os.Handler myHandler = new android.os.Handler();

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mp.getCurrentPosition();
            textViewStart.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBarSong.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }

    };
    String ten;

    private void playSong() {
        intent = getIntent();
        Bundle b = new Bundle();
        String jsonListMusic = intent.getStringExtra("arr_song");
        Log.e("jsong", jsonListMusic);

        arrSong = new Gson().fromJson(jsonListMusic, SongMusic[].class);

        for (int i = 0; i < arrSong.length; i++) {

            arrSong[i] = (SongMusic) arrSong[i];

        }

        position = intent.getIntExtra("position", 0);
        itemPlay = (SongMusic) arrSong[position];

        mp = MediaPlayer.create(this, Uri.parse(itemPlay.getPath()));

        tvTenBaiHat = (TextView) findViewById(R.id.tvTenBaiHat);

        ten = intent.getStringExtra("ten");
        tvTenBaiHat.setText(ten);

        for (int i = 0; i < arrSong.length; i++) {

            mp.start();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ShadowImageView view = new ShadowImageView(this);
        setContentView(R.layout.activity_choi_nhac);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // mp.start();
        // khai bao
        playSong();

        mBackgroundImage = (ImageView) findViewById(R.id.background_image);
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        textViewEnd = (TextView) findViewById(R.id.textViewEndchoinhac);
        ibPlay = (ImageButton) findViewById(R.id.imageButtonPlayChoiNhac);
        ibStop = (ImageButton) findViewById(R.id.imageButtonStop1);
        ibPrevious = (ImageButton) findViewById(R.id.imageButtonBackchoinhac);
        ibLapLai = (ImageButton) findViewById(R.id.imageLapLai);
        ibNgauNhien = (ImageButton) findViewById(R.id.imageButtonNgauNhien);
        ibNext = (ImageButton) findViewById(R.id.imageButtonNext1);

        //WebView webView = (WebView) findViewById(R.id.wvAnh);
        seekBarSong = (SeekBar) findViewById(R.id.seekBarSong);
        //webView.addView(view);
        // lấy thời gian để xét vào seekBarSong
        seekBarSong.setMax(mp.getDuration());

        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                    seekBar.setProgress(progress);
                } else {

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ibPlay.setVisibility(View.INVISIBLE);
        finalTime = mp.getDuration();
        startTime = mp.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekBarSong.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        textViewEnd.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        textViewStart.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );

        seekBarSong.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        // myHandler.postDelayed(UpdateSongTime1, 100);
        // view.startLayoutAnimation();

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibStop.setVisibility(View.VISIBLE);
                ibPlay.setVisibility(View.INVISIBLE);
                if (mp != null && mp.isPlaying()) {
                    mp.pause();
                } else {
                    mp.start();
                }
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            int index = 1;

            @Override
            public void onClick(View v) {

                ibPlay.setVisibility(View.INVISIBLE);
                ibStop.setVisibility(View.VISIBLE);
                mp.stop();
                mp.release();
                index++;
                SongMusic songMusic = arrSong[position + index];
                mp = MediaPlayer.create(ChoiNhacActivity.this, Uri.parse(songMusic.getPath()));
                mp.start();
                seekBarSong.setMax(mp.getDuration());
            }
        });

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            int index = 1;

            @Override
            public void onClick(View v) {

                ibPlay.setVisibility(View.INVISIBLE);
                ibStop.setVisibility(View.VISIBLE);
                mp.stop();
                mp.release();
                index--;
                SongMusic songMusic = arrSong[position + index];
                mp = MediaPlayer.create(ChoiNhacActivity.this, Uri.parse(songMusic.getPath()));
                mp.start();
                seekBarSong.setMax(mp.getDuration());
            }
        });


        ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibPlay.setVisibility(View.VISIBLE);
                ibStop.setVisibility(View.INVISIBLE);
                if (mp.isPlaying()) {
                    mp.pause();
                } else {
                    mp.start();
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mp.stop();
                    }
                });
            }
        });

        ibLapLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.setLooping(true);

                mp.start();
            }
        });
        ibNgauNhien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "off", Toast.LENGTH_LONG).show();
                } else {
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "on", Toast.LENGTH_LONG).show();
                    isRepeat = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_main, menu);

        ActionBar mActionBar = getActionBar();
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(true);
//            case R.id.back:
//                NavUtils.navigateUpFromSameTask(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_LONG).show();
            //TODO
            // xử lý quay lại
            
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

