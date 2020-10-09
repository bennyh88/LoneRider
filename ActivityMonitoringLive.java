package com.example.lonerider001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ActivityMonitoringLive extends AppCompatActivity {

    String TAG = "monitoringLiveTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_live);

        final Intent monServInt = new Intent(this, MonitoringService.class);
        startService(monServInt);
        


        Button finish_activity_button = findViewById(R.id.finish_activity_button);
        finish_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent monServInt = new Intent(this, MonitoringService.class);
                stopService(monServInt);

                Intent MainActIntent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(MainActIntent);
            }
        });


    }

    @Subscribe
    public void monitoringServiceEventReceived(MonitoringServiceEvent event){

        String status = event.status;
        String colour = event.colour;
        String latitude = event.latitude;
        String longitude = event.longitude;


        Log.i(TAG, "event = " + event);
        Log.i(TAG, "status = " + status);
        Log.i(TAG, "colour = " + colour);
        Log.i(TAG, "Lat: " + latitude + " Lon: " + longitude);


        TextView status_textview = findViewById(R.id.activity_status_textview);
        status_textview.setText(status);

        switch (colour) {
            case "GREEN":
                status_textview.setBackgroundColor(getResources().getColor(R.color.colorStatusGreen));
                break;
            case "AMBER":
                Log.i(TAG, "AMBER");
                status_textview.setBackgroundColor(getResources().getColor(R.color.colorStatusAmber));
                break;
            case "RED":
                status_textview.setBackgroundColor(getResources().getColor(R.color.colorStatusRed));
                break;
            case "WHITE":
                status_textview.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                break;
        }


        TextView location_textview = findViewById(R.id.current_location_textview);
        location_textview.setText("Lat: " + latitude + "\nLon: " + longitude);

    }


    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
