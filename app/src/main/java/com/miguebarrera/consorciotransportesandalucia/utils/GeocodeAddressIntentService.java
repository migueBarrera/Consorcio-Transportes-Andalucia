package com.miguebarrera.consorciotransportesandalucia.utils;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.ResultReceiver;

import java.util.List;
import java.util.Locale;

/**
 * Created by migueBarreraBluumi on 18/01/2018.
 */

public class GeocodeAddressIntentService extends IntentService {

    protected ResultReceiver resultReceiver;
    private static final String TAG = "FetchAddyIntentService";

    public GeocodeAddressIntentService() {
        super("GeocodeAddressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        resultReceiver = intent.getParcelableExtra("1");
        int fetchType = intent.getIntExtra("2", 0);

    }
}