package com.example.lonerider001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements UILocationInterface {

    String TAG = "MainActivityTag";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_settings:
                Log.i(TAG, "onOptionsItemSelected: action_settings was pressed");
                Intent intentSettings = new Intent(this, ActivitySettings.class);
                startActivity(intentSettings);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationTrack locationTrack = new LocationTrack(MainActivity.this);
        locationTrack.setUILocationInterface(this);

        TextView location_textview = findViewById(R.id.current_location_textview);
        double Lat1 = locationTrack.getLatitude();
        double Lon1 = locationTrack.getLongitude();
        location_textview.setText("Lat: " + Lat1 + "\n Lon: " + Lon1);



        Button start_activity_button = findViewById(R.id.start_activity_button);
        start_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                String rationale = "Please provide location permission so that you can ...";
                Permissions.Options options = new Permissions.Options()
                        .setRationaleDialogTitle("Info")
                        .setSettingsDialogTitle("Warning");

                Permissions.check(view.getContext(), permissions, rationale, options, new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        Log.i(TAG, "Granted");

                        Intent intent = new Intent(MainActivity.this, ActivityMonitoringLive.class);
                        MainActivity.this.startActivity(intent);

                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        // permission denied, block the feature.
                        Log.i(TAG, "permission denied");
                        Toast.makeText(MainActivity.this, "Please allow Location Services to continue",
                                Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }

    @Override
    public void updateUi(double Lat, double Lon) {
        Log.i(TAG, "Lat = " + Lat + " Lon = " + Lon);
        TextView location_textview = findViewById(R.id.current_location_textview);
        location_textview.setText("Lat: " + Lat + "\n Lon: " + Lon);
    }

}
