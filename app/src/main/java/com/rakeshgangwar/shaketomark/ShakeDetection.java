package com.rakeshgangwar.shaketomark;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.provider.Settings;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Rakesh on 9/7/2016.
 */
public class ShakeDetection implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener){
        this.mListener=listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mListener!=null){
            float x=event.values[0];
            float y=event.values[1];
            float z=event.values[2];

            float gX=x / SensorManager.GRAVITY_EARTH;
            float gY=y / SensorManager.GRAVITY_EARTH;
            float gZ=z / SensorManager.GRAVITY_EARTH;

            Float f=new Float(gX*gX + gY*gY + gZ*gZ);
            Double d=Math.sqrt(f.doubleValue());
            float gForce=d.floatValue();

            if(gForce>SHAKE_THRESHOLD_GRAVITY){
                final long now= System.currentTimeMillis();
                if(mShakeTimestamp+SHAKE_SLOP_TIME_MS > now){
                    return;
                }

                if(mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount=0;
                }

                mShakeTimestamp = now;
                mShakeCount++;

                mListener.onShake(mShakeCount);

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
