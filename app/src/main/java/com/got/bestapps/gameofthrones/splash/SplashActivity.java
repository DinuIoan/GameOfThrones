package com.got.bestapps.gameofthrones.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.database.InitializeDatabase;
import com.got.bestapps.gameofthrones.model.Game;
import com.got.bestapps.gameofthrones.services.IncrementLifesService;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private IncrementLifesService incrementLifesService;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // set the content view for your splash screen you defined in an xml file
        setContentView(R.layout.activity_splash);
        // perform other stuff you need to do
        incrementLifesService
                = new IncrementLifesService(getApplicationContext());
        // execute your xml news feed loader
        Handler handler = new Handler();
        doInBackground();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        }, 2000);

    }

    private void doInBackground(){
        // load your xml feed asynchronously
        db = new DatabaseHandler(SplashActivity.this);
        if (db.getAllQuestions().size() < 1 ) {
            InitializeDatabase.initializeDatabase(db, SplashActivity.this);
        }

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
        if (incrementLifesService.getLifes() != 0 ) {
            List<Game> gameList = db.getAllGames();
            if (gameList != null && gameList.size() != 0) {
                int lifesUpdate =
                        incrementLifesService.getLifes() +
                                gameList.get(0).getGames_number();
                db.modifyGameObject(lifesUpdate, 0);
            }
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