package compindia.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by compindi on 30-10-2017.
 */

public class MusicPlayerSplash extends AppCompatActivity {
    TextView txtMusic;
    ImageView imgAK;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.music_splash);
        imgAK = (ImageView) findViewById(R.id.img_ak);
        imgAK.setVisibility(View.INVISIBLE);
        txtMusic= (TextView) findViewById(R.id.txt_music);
        TranslateAnimation animation = new TranslateAnimation(0, 0, -700, 0);
        animation.setDuration(1000);
        animation.setRepeatCount(0);
        imgAK.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                imgAK.setVisibility(View.VISIBLE);
                Intent intent=new Intent(MusicPlayerSplash.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                String m="<font color='#FF1814'>M</font>";
                String player="usic Player";
               txtMusic.setVisibility(View.VISIBLE);
                txtMusic.setText(Html.fromHtml(m+player));
            }
        }.start();
    }
}
