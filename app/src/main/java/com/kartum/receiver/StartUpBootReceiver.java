package com.kartum.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kartum.utils.Utils;

/**
 * Created by Kartum Infotech (Bhavesh Hirpara) on 10/3/2017.
 */
public class StartUpBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Utils.restartReminders(context);
        }
    }
}
