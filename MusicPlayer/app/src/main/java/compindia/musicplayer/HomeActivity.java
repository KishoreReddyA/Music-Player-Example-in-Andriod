package compindia.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by compindi on 31-10-2017.
 */

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
    ImageView imgRepeat, imgRepeatOff, imgPlayButton, imgNextButton, imgPreviousButton, imgPauseButton, imgSoundOn, imgSoundOff;
    LinearLayout loutBackgroundSongImage;
    TextView txtSongName, txtSingerName;
    ListView songsListView;
    ArrayList<MusicPlayerData> list;
    MediaPlayer mediaPlayer;
    int currentPosition;
    int previousPosition;
    SeekBar seekBar;
    GifTextView gifTextView;
    CustomAdapter customAdapter;
    ImageView imagePlayButtonList;
    ImageView imgSuffleOn;
    ImageView imgSuffleOff;
    RelativeLayout loutMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        list = new ArrayList<>();
        setValues();
        init();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer = MediaPlayer.create(this, list.get(0).getSong());
        customAdapter = new CustomAdapter(this, list);
        songsListView.setAdapter(customAdapter);
        onClickListeners();
    }

    void init() {
        songsListView = (ListView) findViewById(R.id.list_of_songs);
        loutBackgroundSongImage = (LinearLayout) findViewById(R.id.lout_background_song_image);
        imgPlayButton = (ImageView) findViewById(R.id.img_play_button);
        imgNextButton = (ImageView) findViewById(R.id.img_next_button);
        imgPreviousButton = (ImageView) findViewById(R.id.img_previous_button);
        imgPauseButton = (ImageView) findViewById(R.id.img_pause_button);
        imgSoundOn = (ImageView) findViewById(R.id.img_sound_on);
        imgSoundOff = (ImageView) findViewById(R.id.img_soung_off);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        txtSongName = (TextView) findViewById(R.id.txt_song_name);
        txtSingerName = (TextView) findViewById(R.id.singer_name);
        txtSongName.setText(list.get(currentPosition).getSongName());
        txtSingerName.setText(list.get(currentPosition).getSingerName());
        imgRepeat = (ImageView) findViewById(R.id.img_repeat);
        imgRepeatOff = (ImageView) findViewById(R.id.img_repeat_off);
        imgSuffleOff = (ImageView) findViewById(R.id.img_suffle_off);
        imgSuffleOn = (ImageView) findViewById(R.id.img_suffle_on);
        loutMusic = (RelativeLayout) findViewById(R.id.lout_music);
    }

    void onClickListeners() {
        songsListView.setOnItemClickListener(this);
        imgPlayButton.setOnClickListener(this);
        imgNextButton.setOnClickListener(this);
        imgPreviousButton.setOnClickListener(this);
        imgPauseButton.setOnClickListener(this);
        imgSoundOn.setOnClickListener(this);
        imgSoundOff.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        imgRepeat.setOnClickListener(this);
        imgRepeatOff.setOnClickListener(this);
        imgSuffleOff.setOnClickListener(this);
        imgSuffleOn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_play_button:
                mediaPlayer.setOnCompletionListener(this);
                list.get(currentPosition).setIsplaying(true);
                customAdapter.notifyDataSetChanged();
                mediaPlayer.start();
                progressUpdate();
                imgPlayButton.setVisibility(View.GONE);
                imgPauseButton.setVisibility(View.VISIBLE);
                break;
            case R.id.img_pause_button:
                list.get(currentPosition).setIsplaying(false);
                customAdapter.notifyDataSetChanged();
                imgPlayButton.setVisibility(View.VISIBLE);
                imgPauseButton.setVisibility(View.GONE);
                mediaPlayer.pause();
                break;
            case R.id.img_next_button:
                nextSong();
                setMute();
                break;
            case R.id.img_previous_button:
                previousSong();
                setMute();
                list.get(currentPosition).setIsplaying(true);
                customAdapter.notifyDataSetChanged();
                break;
            case R.id.img_sound_on:
                mediaPlayer.setVolume(0, 0);
                imgSoundOff.setVisibility(View.VISIBLE);
                imgSoundOn.setVisibility(View.GONE);
                break;
            case R.id.img_soung_off:
                mediaPlayer.setVolume(1, 1);
                imgSoundOff.setVisibility(View.GONE);
                imgSoundOn.setVisibility(View.VISIBLE);
                break;
            case R.id.img_repeat:
                imgRepeatOff.setVisibility(View.VISIBLE);
                imgRepeat.setVisibility(View.GONE);
                setRepeat();
                break;
            case R.id.img_repeat_off:
                imgRepeatOff.setVisibility(View.GONE);
                imgRepeat.setVisibility(View.VISIBLE);
                mediaPlayer.setLooping(false);
                break;
            case R.id.img_suffle_off:
                imgSuffleOff.setVisibility(View.GONE);
                imgSuffleOn.setVisibility(View.VISIBLE);
                break;
            case R.id.img_suffle_on:
                imgSuffleOn.setVisibility(View.GONE);
                imgSuffleOff.setVisibility(View.VISIBLE);
                break;
        }
    }

    void previousSong() {
        Random random = new Random();
        previousPosition = currentPosition;
        if (imgSuffleOn.getVisibility() == View.VISIBLE) {
            currentPosition = random.nextInt(11);
            list.get(previousPosition).setIsplaying(false);
        }
        if (currentPosition <= 0) {
            list.get(currentPosition).setIsplaying(false);
            currentPosition = list.size();
        }
        stopPreviousSong();
        list.get(previousPosition).setIsplaying(false);
        mediaPlayer = MediaPlayer.create(this, list.get(--currentPosition).getSong());
        list.get(currentPosition).setIsplaying(true);
        customAdapter.notifyDataSetChanged();
        mediaPlayer.setOnCompletionListener(this);
        songsListView.smoothScrollToPosition(currentPosition);
        mediaPlayer.start();
        txtSongName.setText(list.get(currentPosition).getSongName());
        txtSingerName.setText(list.get(currentPosition).getSingerName());
        loutBackgroundSongImage.setBackgroundResource(list.get(currentPosition).getSongImage());
        imgPauseButton.setVisibility(View.VISIBLE);
        imgPlayButton.setVisibility(View.GONE);
        progressUpdate();
    }

    void nextSong() {
        previousPosition = currentPosition;
        Random random = new Random();
        if (imgSuffleOn.getVisibility() == View.VISIBLE) {
            currentPosition = random.nextInt(11);
            setGifSuffle(currentPosition, previousPosition);
        } else if (++currentPosition >= list.size()) {
            currentPosition = 0;
        }
        stopPreviousSong();
        setGif(currentPosition);
        mediaPlayer = MediaPlayer.create(this, list.get(currentPosition).getSong());
        mediaPlayer.setOnCompletionListener(this);
        songsListView.smoothScrollToPosition(currentPosition);
        mediaPlayer.start();
        txtSongName.setText(list.get(currentPosition).getSongName());
        txtSingerName.setText(list.get(currentPosition).getSingerName());
        loutBackgroundSongImage.setBackgroundResource(list.get(currentPosition).getSongImage());
        imgPauseButton.setVisibility(View.VISIBLE);
        imgPlayButton.setVisibility(View.GONE);
        progressUpdate();
    }

    void setGif(int currentPosition) {
        list.get(currentPosition).setIsplaying(true);
        if (currentPosition > 0) {
            list.get(currentPosition - 1).setIsplaying(false);
        }
        if (currentPosition == 0) {
            list.get(list.size() - 1).setIsplaying(false);
        }
        customAdapter.notifyDataSetChanged();
    }

    void setGifSuffle(int currentPosition, int previousPosition) {
        list.get(currentPosition).setIsplaying(true);
        list.get(previousPosition).setIsplaying(false);
        customAdapter.notifyDataSetChanged();
    }

    void setValues() {
        list.add(new MusicPlayerData(R.drawable.song_image2, R.string.song2_name, R.string.song2_singer, R.raw.bhula_dena, false));
        list.add(new MusicPlayerData(R.drawable.song_image1, R.string.song1_name, R.string.song1_singer, R.raw.aasan_nahin_yahan, false));
        list.add(new MusicPlayerData(R.drawable.song_image3, R.string.song3_name, R.string.song3_singer, R.raw.evevo_kalalu, false));
        list.add(new MusicPlayerData(R.drawable.song_image4, R.string.song4_name, R.string.song4_singer, R.raw.oosupodu, false));
        list.add(new MusicPlayerData(R.drawable.image_5, R.string.song5_name, R.string.song5_singer, R.raw.bombhat, false));
        list.add(new MusicPlayerData(R.drawable.image_6, R.string.song6_name, R.string.song6_singer, R.raw.edo_jarugutondi, false));
        list.add(new MusicPlayerData(R.drawable.image_7, R.string.song10_name, R.string.song10_singer, R.raw.fidaa, false));
        list.add(new MusicPlayerData(R.drawable.image_8, R.string.song8_name, R.string.song8_singer, R.raw.sunn_raha_hai, false));
        list.add(new MusicPlayerData(R.drawable.image_9, R.string.song9_name, R.string.song9_singer, R.raw.nuvvele_nuvvele, false));
        list.add(new MusicPlayerData(R.drawable.ashique, R.string.song7_name, R.string.song7_singer, R.raw.meri_aashiqui, false));
        list.add(new MusicPlayerData(R.drawable.image_10, R.string.song11_name, R.string.song11_singer, R.raw.hey_pillagaada, false));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        stopPreviousSong();
        currentPosition = position;
        mediaPlayer = MediaPlayer.create(this, list.get(position).getSong());
        songsListView.smoothScrollToPosition(position);
        mediaPlayer.start();
        progressUpdate();
        int imagePosition = list.get(position).getSongImage();
        loutBackgroundSongImage.setBackgroundResource(imagePosition);
        imgPauseButton.setVisibility(View.VISIBLE);
        imgPlayButton.setVisibility(View.GONE);
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(position).setIsplaying(true);
            } else {
                list.get(i).setIsplaying(false);
            }
        }
        customAdapter.notifyDataSetChanged();
        txtSongName.setText(list.get(position).getSongName());
        txtSingerName.setText(list.get(position).getSingerName());
        setMute();
        setRepeat();
    }

    void stopPreviousSong() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {
        if (b) {
            mediaPlayer.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (imgRepeatOff.getVisibility() == View.VISIBLE) {
            setRepeat();
        } else {
            nextSong();
        }
    }

    void progressUpdate() {
        CountDownTimer countDownTimer = null;
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        seekBar.setMax(mediaPlayer.getDuration());
        countDownTimer = new CountDownTimer(mediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    void setMute() {
        if (imgSoundOff.getVisibility() == View.VISIBLE) {
            mediaPlayer.setVolume(0, 0);
        }
    }

    void setRepeat() {
        mediaPlayer.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar.make(loutMusic, "Ayoure  sure want to close Music Player", Snackbar.LENGTH_LONG);
        snackbar.setAction("YES", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                finish();
            }
        }).show();
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));

    }
}
