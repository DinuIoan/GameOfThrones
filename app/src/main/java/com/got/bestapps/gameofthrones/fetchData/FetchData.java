package com.got.bestapps.gameofthrones.fetchData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.R;

public class FetchData extends Activity implements Loading.LoadingTaskFinishedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_horizontal);
        new Loading(progressBar, this, FetchData.this).execute("");

    }

    @Override
    public void onTaskFinished() {
        completeSplash();
    }
    private void completeSplash(){
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }
    private void startApp() {
        Intent intent = new Intent(FetchData.this, MainActivity.class);
        startActivity(intent);
    }
}
