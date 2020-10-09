package com.example.lonerider001;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ActivitySettings extends AppCompatActivity {

    String TAG = "ActivitySettingsTag";

    int CHECK_INTERVAL;
    int MINIMUM_MOVE_DISTANCE;
    int AUTO_RESUME_RADIUS;
    boolean LIVE_LOCATION;
    boolean PLAY_SOUND;

    int[] CHECK_INTERVAL_ARRAY = {1, 2, 5, 10, 15, 20, 30, 45, 60, 120};
    int[] MINIMUM_MOVE_DISTANCE_ARRAY = {10, 20, 50, 100, 200, 500, 1000, 2000, 5000};
    int[] AUTO_RESUME_RADIUS_ARRAY = {10, 20, 30, 50, 100, 200, 500, 1000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        loadSettings();

        // check interval
        SeekBar check_int_seekBar = findViewById(R.id.check_interval_seekbar);
        check_int_seekBar.setOnSeekBarChangeListener(checkIntseekBarChangeListener);
        check_int_seekBar.setMax(CHECK_INTERVAL_ARRAY.length);
        TextView check_int_text = findViewById(R.id.check_interval_text);

        // minimum move distance
        SeekBar min_move_distance_seekbar = findViewById(R.id.min_move_distance_seekbar);
        min_move_distance_seekbar.setOnSeekBarChangeListener(minMoveDistseekBarChangeListener);
        min_move_distance_seekbar.setMax(MINIMUM_MOVE_DISTANCE_ARRAY.length);
        TextView min_move_distance_text = findViewById(R.id.min_move_distance_text);

        // auto resume radius
        SeekBar resume_radius_seekbar = findViewById(R.id.resume_radius_seekbar);
        resume_radius_seekbar.setOnSeekBarChangeListener(resumeRadiusseekBarChangeListener);
        resume_radius_seekbar.setMax(AUTO_RESUME_RADIUS_ARRAY.length);
        TextView resume_radius_text = findViewById(R.id.resume_radius_text);


        //Set the seekbar feedback text boxes
        check_int_seekBar.setProgress(CHECK_INTERVAL);
        check_int_text.setText(CHECK_INTERVAL_ARRAY[CHECK_INTERVAL] + " minutes");

        min_move_distance_seekbar.setProgress(MINIMUM_MOVE_DISTANCE);
        min_move_distance_text.setText(MINIMUM_MOVE_DISTANCE_ARRAY[MINIMUM_MOVE_DISTANCE] + " metres");

        resume_radius_seekbar.setProgress(AUTO_RESUME_RADIUS);
        resume_radius_text.setText(AUTO_RESUME_RADIUS_ARRAY[AUTO_RESUME_RADIUS] + " metres");


        //Set the switches
        Switch liveLocation = findViewById(R.id.live_location_switch);
        liveLocation.setChecked(LIVE_LOCATION);
        liveLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LIVE_LOCATION = !LIVE_LOCATION;
            }
        });

        Switch playSound = findViewById(R.id.sound_switch);
        playSound.setChecked(PLAY_SOUND);
        playSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PLAY_SOUND = !PLAY_SOUND;
            }
        });

    }


    SeekBar.OnSeekBarChangeListener checkIntseekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            if (fromUser) {
                CHECK_INTERVAL = progress;
                TextView check_int_text = findViewById(R.id.check_interval_text);
                check_int_text.setText(CHECK_INTERVAL_ARRAY[CHECK_INTERVAL] + " minutes");
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            Log.i(TAG, "startTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            TextView check_int_text = findViewById(R.id.check_interval_text);
            check_int_text.setText(CHECK_INTERVAL_ARRAY[CHECK_INTERVAL] + " minutes");
        }
    };

    SeekBar.OnSeekBarChangeListener minMoveDistseekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            if (fromUser) {
                MINIMUM_MOVE_DISTANCE = progress;
                TextView min_move_distance_text = findViewById(R.id.min_move_distance_text);
                min_move_distance_text.setText(MINIMUM_MOVE_DISTANCE_ARRAY[MINIMUM_MOVE_DISTANCE] + " metres");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            Log.i(TAG, "startTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            TextView min_move_distance_text = findViewById(R.id.min_move_distance_text);
            min_move_distance_text.setText(MINIMUM_MOVE_DISTANCE_ARRAY[MINIMUM_MOVE_DISTANCE] + " metres");
        }
    };

    SeekBar.OnSeekBarChangeListener resumeRadiusseekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            if (fromUser) {
                AUTO_RESUME_RADIUS = progress;
                TextView resume_radius_text = findViewById(R.id.resume_radius_text);
                resume_radius_text.setText(AUTO_RESUME_RADIUS_ARRAY[AUTO_RESUME_RADIUS] + " metres");
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            Log.i(TAG, "startTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            TextView resume_radius_text = findViewById(R.id.resume_radius_text);
            resume_radius_text.setText(AUTO_RESUME_RADIUS_ARRAY[AUTO_RESUME_RADIUS] + " metres");
        }
    };




    public void editContacts(View v) {

        Intent intent = new Intent(ActivitySettings.this, ActivityContactsLayout.class);
        ActivitySettings.this.startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        saveSettings();

        Intent intent = new Intent(ActivitySettings.this, MainActivity.class);
        ActivitySettings.this.startActivity(intent);
    }

    public void saveSettings() {

        SharedPreferences sp = getSharedPreferences("SHARED_PREF_SETTINGS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("CHECK_INTERVAL", CHECK_INTERVAL);
        editor.putInt("MINIMUM_MOVE_DISTANCE", MINIMUM_MOVE_DISTANCE);
        editor.putInt("AUTO_RESUME_RADIUS", AUTO_RESUME_RADIUS);
        editor.putBoolean("LIVE_LOCATION", LIVE_LOCATION);
        editor.putBoolean("PLAY_SOUND", PLAY_SOUND);

        editor.commit();

    }

    public void loadSettings() {

        loadContactsSettings();

        SharedPreferences sp = getSharedPreferences("SHARED_PREF_SETTINGS", MODE_PRIVATE);

        CHECK_INTERVAL = sp.getInt("CHECK_INTERVAL", 5);
        MINIMUM_MOVE_DISTANCE = sp.getInt("MINIMUM_MOVE_DISTANCE", 5);
        AUTO_RESUME_RADIUS = sp.getInt("AUTO_RESUME_RADIUS", 5);
        LIVE_LOCATION = sp.getBoolean("LIVE_LOCATION", false);
        PLAY_SOUND = sp.getBoolean("PLAY_SOUND", false);

    }


    int[] contactLines = {R.id.contact_line0, R.id.contact_line1, R.id.contact_line2, R.id.contact_line3};
    public void loadContactsSettings() {
        SharedPreferences sp = getSharedPreferences("SHARED_PREF_CONTACTS", MODE_PRIVATE);

        for (int i=0; i<4; i++){
            String NAME_KEY = "nameline" + i;
            String NUMBER_KEY = "numberline" + i;

            String name = sp.getString(NAME_KEY, "...");
            String number = sp.getString(NUMBER_KEY, "...");
            if (number != null) {
                TextView line = findViewById(contactLines[i]);
                line.setText(name + " " + number);
            }
        }
    }

}
