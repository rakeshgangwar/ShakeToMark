package com.rakeshgangwar.shaketomark;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.rakeshgangwar.shaketomark.database.DBHelper;
import com.rakeshgangwar.shaketomark.database.Locations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShakeService extends Service implements LocationListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private ShakeDetection shakeDetection;
    private DBHelper dbHelper;

    public ShakeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent notificationIntent=new Intent(this, MapsActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_pause_dark)
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();
        startForeground(1337, notification);
        dbHelper=new DBHelper(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetection = new ShakeDetection();
        shakeDetection.setOnShakeListener(new ShakeDetection.OnShakeListener() {
            @Override
            public void onShake(int count) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                        .setContentTitle("Location Registered!")
                        .setContentText("Hi, Location has been registered!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_ALL);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(getNotificationId(), builder.build());
                if(dbHelper.insertCoordinates("26.8467","80.9462","3")){
                    Toast.makeText(getApplicationContext(),"Location Registered",Toast.LENGTH_LONG).show();
                }
            }
        });
        sensorManager.registerListener(shakeDetection, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public int getNotificationId(){
        Date date=new Date();
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(date));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
