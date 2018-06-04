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

public class SplashActivity extends AppCompatActivity {
        private DatabaseHandler db;
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            // set the content view for your splash screen you defined in an xml file
            setContentView(R.layout.activity_splash);
            // perform other stuff you need to do

            // execute your xml news feed loader
            new AsyncLoadXMLFeed().execute();

        }

        private class AsyncLoadXMLFeed extends AsyncTask<String, Integer, Integer> {
            @Override
            protected void onPreExecute(){
                // show your progress dialog

            }

            @Override
            protected Integer doInBackground(String... voids){
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
                return 1234;
            }

            @Override
            protected void onPostExecute(Integer params){
                // dismiss your dialog
                // launch your News activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }

        }
    }