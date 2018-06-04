package com.got.bestapps.gameofthrones;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.database.InitializeDatabase;
import com.got.bestapps.gameofthrones.game.CountdownActivity;
import com.got.bestapps.gameofthrones.game.GameActivity;
import com.got.bestapps.gameofthrones.model.AppInfo;
import com.got.bestapps.gameofthrones.rules.RulesActivity;
import com.got.bestapps.gameofthrones.stats.StatsActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Button statsButton;
    private Button rulesButton;
    private boolean checkTime;
    private TextView gamesNumberTextView;
    private int gamesAvailableNumber;

    private DatabaseHandler databaseHandler;

    private TimeAsyncTask timeAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

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
        //Start updating games
        timeAsyncTask = new TimeAsyncTask(DatabaseData.getAppInfo().getLastTimePlayed());
        timeAsyncTask.execute();

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
    @Override
    public void onBackPressed() {
        makeTimeUpdate();
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
        timeAsyncTask = new TimeAsyncTask(DatabaseData.getAppInfo().getLastTimePlayed());
        timeAsyncTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        makeTimeUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        makeTimeUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        makeTimeUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gamesAvailableNumber = DatabaseData.getGame().getGames_number();
        gamesNumberTextView.setText("" + gamesAvailableNumber);
        timeAsyncTask = new TimeAsyncTask(DatabaseData.getAppInfo().getLastTimePlayed());
        timeAsyncTask.execute();

    }

    private void makeTimeUpdate() {
        timeAsyncTask.cancel(true);
        AppInfo appInfo = new AppInfo(0L, (System.currentTimeMillis() - DatabaseData.getAppInfo().getLastTimePlayed()) + System.currentTimeMillis() );
        databaseHandler.addAppInfo(appInfo);
    }

    public class TimeAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        private Date lastTimePlayed;


        public TimeAsyncTask(long lastTimePlayed) {
            this.lastTimePlayed = new Date(lastTimePlayed);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            while (checkTime) {
                if (databaseHandler.getAllGames().get(0).getGames_number() != 7 ) {
                    Date currentDate = new Date(System.currentTimeMillis());
                    long goneTime = (currentDate.getTime() - lastTimePlayed.getTime()) / 1000;
                    if (goneTime > 3600) {
                        long timeRemaining = goneTime / 3600;
                        int lifesEarned = 0;
                        int numberOfGamesFromDB = DatabaseData.getGame().getGames_number();

                        if (goneTime / 3600 > 7)
                            lifesEarned = 7;
                        else
                            lifesEarned = (int) goneTime / 3600;

                        if (numberOfGamesFromDB + lifesEarned > 7) {
                            databaseHandler.modifyGameObject(7, 0);
                        } else {
                            databaseHandler.modifyGameObject(numberOfGamesFromDB + lifesEarned, 0);
                        }
                        DatabaseData.setGame(databaseHandler.getGameById(0));
                        gamesNumberTextView.setText("" + DatabaseData.getGame().getGames_number());
                        AppInfo appInfo = new AppInfo(0L, System.currentTimeMillis() + timeRemaining);
                        databaseHandler.addAppInfo(appInfo);
                        DatabaseData.setAppInfo(appInfo);
                    }
                }
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }



}
