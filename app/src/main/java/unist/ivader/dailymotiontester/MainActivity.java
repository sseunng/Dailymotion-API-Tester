package unist.ivader.dailymotiontester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private PlayerWebView playerWebView;
    private TextView length, current, currentQuality;
    private String videoID = "x7s5qhy";

    private Runnable qualityChecker = new qualityCheckerRunnable();
    private Thread qualityCheckerThread = new Thread(qualityChecker);

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerWebView = findViewById(R.id.dm_player_web_view);
        playerWebView.load(videoID);
        qualityCheckerThread.start();

        Button load = findViewById(R.id.load);
        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button mute = findViewById(R.id.mute);
        Button unmute = findViewById(R.id.unmute);

        Button q380 = findViewById(R.id.q380);
        Button q480 = findViewById(R.id.q480);
        Button q720 = findViewById(R.id.q720);
        Button q1080 = findViewById(R.id.q1080);

        Button lengthBtn = findViewById(R.id.videoLength);
        Button currentBtn = findViewById(R.id.currentTime);
        length = findViewById(R.id.length);
        current = findViewById(R.id.current);
        currentQuality = findViewById(R.id.currentQuality);

        Spinner videoSelector = findViewById(R.id.videoselector);
        ArrayAdapter<CharSequence> videoIdAdapter = ArrayAdapter.createFromResource(this, R.array.videoid, android.R.layout.simple_spinner_dropdown_item);
        videoSelector.setAdapter(videoIdAdapter);
        videoSelector.setSelection(0);
        videoSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 :
                        videoID = "x7s5qhy";
                        break;
                    case 1 :
                        videoID = "x7s71dt";
                        break;
                    case 2 :
                        videoID = "x7s1of4";
                        break;
                    case 3 :
                        videoID = "x7s21hq";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        load.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playerWebView.load(videoID);
                return false;
            }
        });

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playerWebView.play();
                return false;
            }
        });

        pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playerWebView.pause();
                return false;
            }
        });

        mute.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playerWebView.mute();
                return false;
            }
        });

        unmute.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playerWebView.unmute();
                return false;
            }
        });

        q380.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playerWebView.setQuality("380");
                return false;
            }
        });
        q480.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playerWebView.setQuality("480");
                return false;
            }
        });
        q720.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playerWebView.setQuality("720");
                return false;
            }
        });
        q1080.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playerWebView.setQuality("1080");
                return false;
            }
        });

        lengthBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                double lengthDouble = playerWebView.getDuration();

                int hour, min, sec;
                if (lengthDouble >= 3600) {
                    hour = (int) lengthDouble / 3600;
                    min = (int) (lengthDouble - (hour * 3600)) / 60;
                    sec = (int) (lengthDouble - (hour * 3600)) % 60;
                    length.setText(hour + ":" + min + ":" + sec);
                }
                else if (lengthDouble < 3600 && lengthDouble >= 60) {
                    min = (int) lengthDouble / 60;
                    sec = (int) lengthDouble - (min * 60);
                    length.setText(min + ":" + sec);
                }
                else if (lengthDouble < 60) {
                    sec = (int) lengthDouble;
                    if (sec < 10) {
                        length.setText("00:0" + sec);
                    } else {
                        length.setText("00:" + sec);
                    }
                }
                return false;
            }
        });

        currentBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                double currentDouble = playerWebView.getPosition();
                currentDouble = currentDouble / 1000;
                int hour, min, sec;
                if (currentDouble >= 3600) {
                    hour = (int) currentDouble / 3600;
                    min = (int) (currentDouble - (hour * 3600)) / 60;
                    sec = (int) (currentDouble - (hour * 3600)) % 60;
                    current.setText(hour + ":" + min + ":" + sec);
                }
                else if (currentDouble < 3600 && currentDouble >= 60) {
                    min = (int) currentDouble / 60;
                    sec = (int) currentDouble - (min * 60);
                    current.setText(min + ":" + sec);
                }
                else if (currentDouble < 60) {
                    sec = (int) currentDouble;
                    if (sec < 10) {
                        current.setText("00:0" + sec);
                    } else {
                        current.setText("00:" + sec);
                    }
                }
                return false;
            }
        });

        // playerWebView.isEnded();
        // 비디오 끝나면 영상 minimize 하도록 하기

    }

    @SuppressLint("HandlerLeak")
    Handler qualityHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            currentQuality.setText("Current quality : " + msg.obj.toString() + "p");
        }
    };

    private class qualityCheckerRunnable extends Thread implements Runnable {
        @Override
        public void run() {
            super.run();

            TimerTask checker = new TimerTask() {
                @Override
                public void run() {
                    String quality = playerWebView.getQuality();
                    qualityHandler.sendMessage(Message.obtain(qualityHandler, 1, quality));
                }
            };

            Timer timer = new Timer();
            timer.schedule(checker, 0, 1000);
        }
    }
}
