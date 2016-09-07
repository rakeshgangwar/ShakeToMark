package com.rakeshgangwar.shaketomark;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rakesh on 9/7/2016.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent=new Intent(context,ShakeService.class);
        context.startService(serviceIntent);
    }
}
