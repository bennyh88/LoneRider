package com.example.lonerider001;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


public class ActivityContactsLayout extends AppCompatActivity {

    String TAG = "ActivityContactsLayoutTag";

    String[] NameList = {"...", "...", "...", "..."};
    String[] NumberList = {"...", "...", "...", "..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);

        Button saveContactsButton = findViewById(R.id.save_contacts_button);
        saveContactsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveContacts();
            }
        });

        loadContacts();
        displayContacts();

    }


    private void saveContacts() {
        SharedPreferences sp = getSharedPreferences("SHARED_PREF_CONTACTS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        for (int i=0; i<4; i++){
            String NAME_KEY = "nameline" + i;
            String NUMBER_KEY = "numberline" + i;

            String name = NameList[i];
            String number = NumberList[i];
            if (number != null) {
                editor.putString(NAME_KEY, name);
                editor.putString(NUMBER_KEY, number);

            }
            else {
                editor.putString(NAME_KEY, "...");
                editor.putString(NUMBER_KEY, "...");
            }

        }
        editor.apply();

        Intent intent = new Intent(this, ActivitySettings.class);
        this.startActivity(intent);

    }

    private void loadContacts() {
        SharedPreferences sp = getSharedPreferences("SHARED_PREF_CONTACTS", MODE_PRIVATE);

        for (int i=0; i<4; i++){
            String NAME_KEY = "nameline" + i;
            String NUMBER_KEY = "numberline" + i;

            String name = sp.getString(NAME_KEY, "...");
            String number = sp.getString(NUMBER_KEY, "...");
            if (number != null) {
                NumberList[i] = number;
                NameList[i] = name;
            }
            Log.i(TAG, NameList[i] + "-" + NumberList[i]);

        }
        Log.i(TAG, "Contacts Loaded");

    }

    int[] editButtons = {R.id.edit_buttonln0, R.id.edit_buttonln1, R.id.edit_buttonln2, R.id.edit_buttonln3};
    //String[] editButtons = {"edit_buttonln0", "edit_buttonln1", "edit_buttonln2", "edit_buttonln3"};
    int[] contactTexts = {R.id.contact_textln0, R.id.contact_textln1, R.id.contact_textln2, R.id.contact_textln3};

    private void displayContacts() {
        sortContacts();
        Log.i(TAG, "displayContacts()");
        for (int i=0; i<4; i++) {
            TextView line = findViewById(contactTexts[i]);
            Button button = findViewById(editButtons[i]);
            if (NameList[i] != null){
                line.setText(NameList[i] + " " + NumberList[i]);
                Log.i(TAG, NameList[i] + " " + NumberList[i]);

                if (NameList[i].equals("...")) {
                    button.setText("ADD");
                }
                else {
                    button.setText("REMOVE");
                }

            }
            else {
                line.setText("...");
            }

        }


    }

    private void sortContacts() {

        checkForDuplicates();

        Log.i(TAG, "sortContacts()");
        for (int i=0; i<4; i++) {
            Log.i(TAG, "NumberList[" + i + "] = " + NumberList[i] + " NameList[" + i + "] = " + NameList[i]);
        }

        for (int i=0; i<4; i++) {
            //Log.i(TAG, "NumberList[" + i + "] = " + NumberList[i] + " NameList[" + i + "] = " + NameList[i]);
            int count = 0;
            while (NameList[i].equals("...") && count < 4) {
                count ++;
                Log.i(TAG,  "i = " + i);
                for (int j=i; j<3; j++) {
                    NumberList[j] = NumberList[j+1];
                    NameList[j] = NameList[j+1];

                    NumberList[j+1] = "...";
                    NameList[j+1] = "...";
                }
            }
        }
        /*
        Log.i(TAG, "sortContacts() post sort");
        for (int i=0; i<4; i++) {
            Log.i(TAG, "NumberList[" + i + "] = " + NumberList[i] + " NameList[" + i + "] = " + NameList[i]);
        }
        */

    }

    private void checkForDuplicates() {
        Log.i(TAG, "checkForDuplicates()");
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (j != i) {
                    if (NumberList[j].equals(NumberList[i])) {
                        if (!NumberList[j].equals("...")) {
                            Log.i(TAG, "Duplicate Found");
                            deleteLine(editButtons[j]);
                        }
                    }
                }
            }
        }
    }


    private void deleteLine(int button) {
        Log.i(TAG, "deleteLine()");
        for (int i=0; i<4; i++) {
            Log.i(TAG, "button = " + button + ",  editButtons[i] = " + editButtons[i]);
            if (button == editButtons[i]) {
                Log.i(TAG, "line to remove found");
                NameList[i] = "...";
                NumberList[i] = "...";
                displayContacts();
            }
        }
    }

    public void chooseContact(View v) {

        Log.i(TAG, "view v = " + v);
        int id = v.getId();
        Log.i(TAG, "id = " + id);
        String idString = "no id";
        if(id != View.NO_ID) {                    // make sure id is valid
            Resources res = v.getResources();     // get resources
            if(res != null)
                idString = res.getResourceEntryName(id); // get id string entry
        }
        // do whatever you want with the string. it will
        // still be "no id" if something went wrong
        //Log.i(TAG, idString);
        Log.i(TAG, "id = " + id);

        Button lineButton = findViewById(id);
        if (lineButton.getText() != "ADD"){
            //deleteLine(idString);
            deleteLine(id);
        }
        else {

            String[] permissions = {Manifest.permission.READ_CONTACTS};
            String rationale = "Please provide location permission so that you can ...";
            Permissions.Options options = new Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning");

            Permissions.check(v.getContext(), permissions, rationale, options, new PermissionHandler() {
                @Override
                public void onGranted() {
                    // do your task.
                    Log.i(TAG, "Granted");
                    //Launch contact picker
                    selectContact();
                }

                @Override
                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                    // permission denied, block the feature.
                    Log.i(TAG, "permission denied");
                    Toast.makeText(ActivityContactsLayout.this, "Please allow Location Services to continue",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private final static int REQUEST_CONTACTPICKER = 1;

    private void selectContact() {
        // This intent will fire up the contact picker dialog
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, REQUEST_CONTACTPICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONTACTPICKER)
        {
            if(resultCode == RESULT_OK)
            {
                Uri contentUri = data.getData();
                String contactId = contentUri.getLastPathSegment();
                Log.i(TAG, contactId);
                Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone._ID + "=?",       // < - Note,_ID not CONTACT_ID! Contacts.DISPLAY_NAME_PRIMARY
                        new String[]{contactId}, null);
                Log.i(TAG, contactId);
                startManagingCursor(cursor);
                Boolean numbersExist = cursor.moveToFirst();
                int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int contactNameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String phoneNumber = "";
                String contactName = "";
                while (numbersExist)
                {
                    phoneNumber = cursor.getString(phoneNumberColumnIndex);
                    phoneNumber = phoneNumber.trim();
                    Log.i(TAG, phoneNumber);

                    contactName = cursor.getString(contactNameColumnIndex);
                    Log.i(TAG, contactName);
                    numbersExist = cursor.moveToNext();
                }
                stopManagingCursor(cursor);
                if (!phoneNumber.equals(""))
                {
                    setPhoneNumber(phoneNumber, contactName);


                } // phoneNumber != ""
            } // Result Code = RESULT_OK
        } // Request Code = REQUEST_CONTACTPICER
    }	// end function


    public void setPhoneNumber(String phoneNumber, String contactName){
        Log.i(TAG, contactName + "-" + phoneNumber);
        NameList[3] = contactName;
        NumberList[3] = phoneNumber;

        Log.i(TAG, "setPhoneNumber()");
        for (int i=0; i<4; i++) {
            Log.i(TAG, "NumberList[" + i + "] = " + NumberList[i] + " NameList[" + i + "] = " + NameList[i]);
        }

        displayContacts();
    }
}
