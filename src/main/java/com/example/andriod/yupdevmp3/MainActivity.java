package com.example.andriod.yupdevmp3;

//import android.icu.util.TimeUnit;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Handler;
//import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //    Button button1,button2,button3,button4;
//    ImageView
    private SeekBar seekbar;
    private MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    public static int oneTimeOnly = 0;
    private double startTime = 0;
    private double finalTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forward = (Button) findViewById(R.id.button1);
        final Button pause = (Button) findViewById(R.id.button2);
        final Button play = (Button) findViewById(R.id.button3);
        final Button backward = (Button) findViewById(R.id.button4);
        Button stop = (Button) findViewById(R.id.button5);

         final TextView t1 = (TextView) findViewById(R.id.mainText2);
        final TextView song_name = (TextView) findViewById(R.id.mainText3);
        final TextView t3 = (TextView) findViewById(R.id.mainText4);
        song_name.setText("Wild Thoughts");

        mediaPlayer = MediaPlayer.create(this, R.raw.wild);
        seekbar = (SeekBar) findViewById(R.id.mainSeekBar);
        seekbar.setClickable(false);
        // disables the button
        pause.setEnabled(false);

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Playing Sound", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                t3.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
                );
                t1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
                );

                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });
        pause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Pausing Sound", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });
        forward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);

                    Toast toast = Toast.makeText(getApplicationContext(), "+ 5 secs", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cant jump forward", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        backward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);

                    Toast toast = Toast.makeText(getApplicationContext(), "- 5 secs", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cant jump backward", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "sound stopped!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                mediaPlayer.stop();
                play.setEnabled(true);
//                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;

            }
        });
    }

       private Runnable UpdateSongTime = new Runnable() {
            public void run() {
                final TextView t1 = (TextView) findViewById(R.id.mainText2);
                startTime = mediaPlayer.getCurrentPosition();
                t1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
                );
                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(this, 100);
            }
        };




    }

