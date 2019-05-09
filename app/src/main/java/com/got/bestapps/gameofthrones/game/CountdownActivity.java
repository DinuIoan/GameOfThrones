package com.got.bestapps.gameofthrones.game;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;

public class CountdownActivity extends AppCompatActivity {
    private TextView countdownTextview;
    private CountDownTimer countDownTimer;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        countdownTextview = (TextView) findViewById(R.id.countdown_textview);
        backButton = findViewById(R.id.back_button);

        this.countDownTimer = new CountDownTimer(4 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                String remaining = "" + millisUntilFinished / 1000;
                countdownTextview.setText(remaining);
            }
            public void onFinish() {
                Intent intent = new Intent(CountdownActivity.this, GameActivity.class);
                startActivity(intent);
            }
        };
        countDownTimer.start();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeBackPress();
            }
        });
    }

    @Override
    public void onBackPressed() {
        executeBackPress();
    }

    private void executeBackPress() {
        countDownTimer.cancel();
        Intent intent = new Intent(CountdownActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
