package com.got.bestapps.gameofthrones.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ionut on 17.09.2018.
 */

public class IncrementLifesBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(IncrementLifesBroadcastReciver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, IncrementLifesService.class));;
    }
}
