package com.got.bestapps.gameofthrones.services;

import android.os.AsyncTask;

import java.util.Date;

public class TimeAsyncTask extends AsyncTask<Integer, Integer, Integer> {
    private Date startTime;


    public TimeAsyncTask(long startTime) {
        this.startTime = new Date(startTime);
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        while (true) {
            Date currentDate = new Date(System.currentTimeMillis());
            long goneTime = (currentDate.getTime() - startTime.getTime()) / 1000 ;
            if (goneTime > 3600 ) {
                int lifesEarned = 0;
                if (goneTime / 3600 > 7)
                    lifesEarned = 7;
                else
                    lifesEarned =(int) goneTime / 3600;
                switch (lifesEarned) {
                    case 1:

                }
            }
            break;
        }
        return 1234;
    }

}
