package unist.ivader.dailymotiontester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dailymotion.android.player.sdk.PlayerWebView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private PlayerWebView playerWebView;
    private Button muteBtn, fullscreenBtn;
    private Button q380, q480, q720, q1080;
    private Button lengthBtn, currentBtn;
    private TextView length, current;
    private Date formattedCurrent;

    private boolean isMuted = false;

    // 일시정지/재생, 뮤트 버튼 제대로 되도록 하기, seek, length 시간 조절하기

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerWebView = findViewById(R.id.dm_player_web_view);
        playerWebView.load("x7s21hq");
        playerWebView.setQuality("1080");

        muteBtn = findViewById(R.id.mute);
        fullscreenBtn = findViewById(R.id.fullscreen);

        q380 = findViewById(R.id.q380);
        q480 = findViewById(R.id.q480);
        q720 = findViewById(R.id.q720);
        q1080 = findViewById(R.id.q1080);

        final DateFormat format = new SimpleDateFormat("HHmmss");

        lengthBtn = findViewById(R.id.videoLength);
        currentBtn = findViewById(R.id.currentTime);
        length = findViewById(R.id.length);
        length.setText("0:00");
        current = findViewById(R.id.current);
        current.setText("0:00");

        muteBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isMuted) {
                    isMuted = false;
                    muteBtn.setText("Mute");
                    playerWebView.unmute();
                } else {
                    isMuted = true;
                    muteBtn.setText("Unmute");
                    playerWebView.mute();
                }
                return false;
            }
        });

        fullscreenBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playerWebView.setFullscreenButton(true);
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
                try {
                    Date formattedLength = format.parse(String.valueOf(lengthDouble));
                    length.setText((CharSequence) formattedLength);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        currentBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                double currentDouble = playerWebView.getPosition();
                try {
                    formattedCurrent = format.parse(String.valueOf(currentDouble));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                current.setText((CharSequence) formattedCurrent);
                return false;
            }
        });

    }
}
