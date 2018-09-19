package com.got.bestapps.gameofthrones;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.database.InitializeDatabase;
import com.got.bestapps.gameofthrones.game.CountdownActivity;
import com.got.bestapps.gameofthrones.rules.RulesActivity;
import com.got.bestapps.gameofthrones.stats.StatsActivity;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Button statsButton;
    private Button rulesButton;
    private boolean checkTime;
    private TextView gamesNumberTextView;
    private int gamesAvailableNumber;

    private static DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_main);

        checkTime = true;

        databaseHandler = new DatabaseHandler(MainActivity.this);
        if (databaseHandler.getAllQuestions().size() < 1 ) {
            InitializeDatabase.initializeDatabase(databaseHandler, getApplicationContext());
        }
        gamesNumberTextView = (TextView) findViewById(R.id.games_text_view);
        playButton = (Button) findViewById(R.id.button_play);
        statsButton = (Button) findViewById(R.id.button_stats);
        rulesButton = (Button) findViewById(R.id.button_rules);

        gamesAvailableNumber = DatabaseData.getGame().getGames_number();

        gamesNumberTextView.setText("" + gamesAvailableNumber);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DatabaseData.getGame().getGames_number() == 0) {

                } else {
                    checkTime = false;
                    Intent intent = new Intent(getApplicationContext(), CountdownActivity.class);
                    startActivity(intent);
                }
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTime = false;
                Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
                startActivity(intent);
            }
        });

        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTime = false;
                Intent intent = new Intent(getApplicationContext(), RulesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void reloadeGames() {
        DatabaseData.setGame(databaseHandler.getAllGames().get(0));
        gamesAvailableNumber = DatabaseData.getGame().getGames_number();
        gamesNumberTextView.setText("" + gamesAvailableNumber);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gamesAvailableNumber = DatabaseData.getGame().getGames_number();
        gamesNumberTextView.setText("" + gamesAvailableNumber);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamesAvailableNumber = DatabaseData.getGame().getGames_number();
        gamesNumberTextView.setText("" + gamesAvailableNumber);

    }
}
