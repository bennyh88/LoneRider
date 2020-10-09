package com.example.lonerider001;

import android.telephony.ServiceState;

import static android.telephony.ServiceState.STATE_IN_SERVICE;

public class StatusManager {

    String status = "status not yet set";
    String colour = "white";

    boolean messageSent = true;


    public void updateStatus() {
        colour = "GREEN"; status = "Monitoring Active";

        if (!networkServiceAvailable()) {
            status = "No cellular connection"; colour = "AMBER";
        }
        if (!contactsNotified()) {
            status = "Emergency contacts not yet notified"; colour = "AMBER";
        }


    }


    private boolean networkServiceAvailable() {

        ServiceState ServiceState = new ServiceState();
        int state = ServiceState.getState();
        if (state != STATE_IN_SERVICE) {
            return false;
        }
        else {
            return true;
        }

    }

    private boolean contactsNotified() {
        if (messageSent) {
            return true;
        }
        else {
            return false;
        }
    }

}
