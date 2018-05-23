package com.got.bestapps.gameofthrones.fetchData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.got.bestapps.gameofthrones.MainActivity;
import com.got.bestapps.gameofthrones.database.DatabaseData;
import com.got.bestapps.gameofthrones.database.DatabaseHandler;
import com.got.bestapps.gameofthrones.database.InitializeDatabase;

public class Loading extends AsyncTask<String, Integer, Integer> {
    public interface LoadingTaskFinishedListener {
        void onTaskFinished();
    }

    private DatabaseHandler db = null;

    private final ProgressBar progressBar;
    private final LoadingTaskFinishedListener finishedListener;
    private final Context context;

    public Loading(ProgressBar progressBar,
                   LoadingTaskFinishedListener finishedListener, Context context) {
        this.progressBar = progressBar;
        this.finishedListener = finishedListener;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        /*
         * database
         */
        db = new DatabaseHandler(context);
        if (db.getAllQuestions().size() < 1 ) {
            InitializeDatabase.initializeDatabase(db, context);
        }

        if (DatabaseData.getQuestions() == null) {
            DatabaseData.setGame(db.getAllGames().get(0));
            DatabaseData.setPlayerState(db.getAllPlayerState().get(0));
            DatabaseData.setQuestions(db.getAllQuestions());
            DatabaseData.setRankings(db.getAllRankings());
        }

        Log.i("Tutorial", "Starting task with url: " + params[0]);
        if (resourcesDontAlreadyExist()) {
            downloadResources();
        }
        return 1234;

    }

    private boolean resourcesDontAlreadyExist() {
        if (DatabaseData.getQuestions() == null) {
            return true;
        }
        return false;
    }

    private void downloadResources() {

        int count = 50;
        for (int i = 0; i < count; i++) {

            int progress = (int) ((i / (float) count) * 100);
            publishProgress(progress);

            try {
                Thread.sleep(50);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        finishedListener.onTaskFinished();
    }
}