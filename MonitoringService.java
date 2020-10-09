package com.example.lonerider001;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.ServiceState;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import static android.telephony.ServiceState.STATE_IN_SERVICE;

public class MonitoringService extends Service implements Runnable {

    String TAG = "monitoringServiceTag";
    int startMode = START_NOT_STICKY;       // indicates how to behave if the service is killed
    IBinder binder;      // interface for clients that bind
    boolean allowRebind; // indicates whether onRebind should be used

    Thread thread;
    LocationTrack LocTrac;
    MonitoringServiceEvent MSevent;
    StatusManager statusManager;
    SMSManager SMSManager;

    volatile boolean latch = true;

    double lat = 0;
    double lon = 0;
    String colour = "white";
    String status = "status";

    int sleepTime = 10000;


    @Override
    public void onCreate() {
        // The service is being created
        Log.i(TAG, "Monitoring Service created");
        thread = new Thread(this);
        LocTrac = new LocationTrack(this);
        MSevent = new MonitoringServiceEvent();
        statusManager = new StatusManager();
        SMSManager = new SMSManager(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
        thread.start();
        Log.i(TAG, "Monitoring Service started");

        return startMode;
    }



    @Override
    public void run() {
        SMSManager.sendStartSMS();
        latch = true;
        while (latch){

            //Log.i(TAG, "RUN!!!");

            lat = LocTrac.getLatitude();
            lon = LocTrac.getLongitude();

            Log.i(TAG, "Lat = " + lat + "Lon = " + lon);


            updateStatus();
            updateUI();

            try {
                Log.i(TAG, "Sleep");
                thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Log.i(TAG, "e = " + e);
                e.printStackTrace();
            }

        }

        Log.i(TAG, "finished the loop");

    }


    private void updateStatus() {
        statusManager.updateStatus();
        status = statusManager.status;
        colour = statusManager.colour;

    }


    private void updateUI() {
        //EventBus.getDefault().post(new MonitoringServiceEvent(Double.toString(lat), Double.toString(lon)));
        MSevent.status = status;
        MSevent.colour = colour;
        MSevent.latitude = Double.toString(lat);
        MSevent.longitude = Double.toString(lon);

        EventBus.getDefault().post(MSevent);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return allowRebind;
    }

    @Override
    public void onRebind(Intent intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    @Override
    public void onDestroy() {
        latch = false;
        thread.interrupt();
        // The service is no longer used and is being destroyed
    }


}



