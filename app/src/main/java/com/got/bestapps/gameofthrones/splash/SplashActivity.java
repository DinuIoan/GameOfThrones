package com.got.bestapps.gameofthrones.splash;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.database.InitializeDatabase;
import com.got.bestapps.gameofthrones.model.Game;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private boolean firstTime = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();
        // set the content view for your splash screen you defined in an xml file
        setContentView(R.layout.activity_splash);
        db = new DatabaseHandler(SplashActivity.this);
        // perform other stuff you need to do
        // execute your xml news feed loader
        checkFirstTime();
        if (firstTime) {
            firstTime = false;
        } else {
            lifeUpdate();
        }
        loadData();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        }, 2000);

    }

    private void checkFirstTime() {
        if (db.getAllQuestions().size() < 1 ) {
            InitializeDatabase.initializeDatabase(db, SplashActivity.this);
            firstTime = true;
        }
    }

    private void lifeUpdate() {
        int oneHourInMillis = 60*60*1000;
        long lastTimePlayed = db.getAppInfo().getLastTimePlayed();
        long actualTime =  System.currentTimeMillis();
        int hoursPassed = Math.toIntExact((actualTime - lastTimePlayed) / oneHourInMillis);
        int remaining = Math.toIntExact(actualTime - (lastTimePlayed + hoursPassed * oneHourInMillis));
        if (hoursPassed > 1) {
            Game game = db.getGameById(0);
            db.modifyGameObject(game.getGames_number() + hoursPassed, 0);
            db.updateAppInfo(actualTime, remaining);
        }
    }

    private void loadData(){
        // load your xml feed asynchronously
        if (DatabaseData.getQuestions() == null) {
            DatabaseData.setQuestions(db.getAllQuestions());
        }
        if (DatabaseData.getGame() == null) {
            DatabaseData.setGame(db.getAllGames().get(0));
        }
        if (DatabaseData.getPlayerState() == null) {
            DatabaseData.setPlayerState(db.getAllPlayerState().get(0));
        }
        if (DatabaseData.getRankings() == null) {
            DatabaseData.setRankings(db.getAllRankings());
        }
        if (DatabaseData.getAppInfo() == null ) {
            DatabaseData.setAppInfo(db.getAppInfo());
        }
    }

    private void onPostExecute(){
        // dismiss your dialog
        // launch your News activity
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);

        // close this activity
        finish();
    }


}