package com.example.lonerider001;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;


public class SMSManager {

    String TAG = "SMSTag";

    String address = "07707387987";

    Context mContext;

    String[] ADDRESS_ARRAY = {"...", "...", "...", "..."};

    public SMSManager(Context appContext){

        mContext = appContext;

        SharedPreferences sp = mContext.getSharedPreferences("SHARED_PREF_CONTACTS", Context.MODE_PRIVATE);
        for (int i=0; i<4; i++){
            String NUMBER_KEY = "numberline" + i;
            String number = sp.getString(NUMBER_KEY, "...");
            if (number != null) {
                ADDRESS_ARRAY[i] = number;
                Log.i(TAG, "number" + i + " " + number);
            }
        }

    }

    public void sendStartSMS() {
        //double Latitude = LocationTrack.getLatitude;
        String message = "Im off fer a bike ride";
        sendSMS(message);
    }

    public void sendFinishSMS() {
        String message = "";
        sendSMS(message);
    }

    public void sendSOSSMS() {
        String message = "";
        sendSMS(message);
    }

    public void sendAlarmSMS() {
        String message = "";
        sendSMS(message);
    }

    private void sendSMS(String message){
         /*
        sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent)
        */


        for (int i=0; i<4; i++) {
            address = ADDRESS_ARRAY[i];
            if (!address.equals("...")) {
                try {
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(address, null, message, null, null);
                    //Toast.makeText(MainActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SMS SENT");
                } catch (Exception e) {
                    //Toast.makeText(MainActivity.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "SMS SEND FAIL");

                }
            }
        }
    }



}
