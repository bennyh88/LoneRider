/*

package com.example.lonerider001;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import android.support.v4.app.ActivityCompat;


public class permissionCheck extends AppCompatActivity {


    String TAG = "permissionCheckTag";

    Activity activity;


    //0 = READ_CONTACTS
    //1 = ACCESS_FINE_LOCATION

    String[] permissions = {"Manifest.permission.READ_CONTACTS", "android.Manifest.permission.ACCESS_FINE_LOCATION"};
    String[] rationale = {"Contact access is required to send distress message",
                            "Location data is used to determine if you are in distress"};
    int[] results = {0,0};

    public permissionCheck(Activity act){
        activity = act;
    }

    private static final int PERMISSION_REQUEST_CODE = 200;

    public boolean checkPermission(int index, Context context) {
        Log.i(TAG, "in check perms");

        results[index] = ContextCompat.checkSelfPermission(context, permissions[index]);
        Log.i(TAG, "in check perms1");

        if (results[index] != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "in check perms2");
            requestPermission();
        }

        return results[index] == PackageManager.PERMISSION_GRANTED;
        //return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        Log.i(TAG, "in req perms");
        //requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
        Log.i(TAG, "in req perms 2");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.i(TAG, "in result 0");
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    for (int i=0; i<permissions.length; i++){
                        Log.i(TAG, "in result 1");

                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {

                                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                                alertDialog.setTitle("Permissions Required");
                                alertDialog.setMessage(rationale[i]);
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermission();
                                            }
                                        } );
                                /*
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        } );


                                alertDialog.show();
                            }

                        }
                        else {
                            //all accepted
                        }
                    }
                }
                break;
        }
    }


    /*
    public Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }
    */


    /*

    String checkPermission(String RESOURCE, Context context, Activity activity){

        switch (RESOURCE) {
            case permissions[0]:
                if (ContextCompat.checkSelfPermission(context, READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    askPermission(RESOURCE, context, activity);
                }
                else {
                    return "PERMISSION_GRANTED";
                }
                break;
            default:
                break;
        }

        return "PERMISSION_DENIED";
    }


    private void askPermission(String RESOURCE, Context context, Activity activity){


        switch (RESOURCE) {
            // action with ID action_refresh was selected
            case READ_CONTACTS:
                Log.i(TAG, "onOptionsItemSelected: action_settings was pressed");

                break;
            default:
                break;
        }

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.RESOURCE)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

    }


}

*/