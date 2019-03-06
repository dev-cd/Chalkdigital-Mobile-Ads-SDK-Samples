package com.chalkdigital.android.cdads_sample;

import android.app.Application;

import com.chalkdigital.common.CDAdsUtils;

import java.util.logging.Level;


/**
 * Created by arungupta on 11/01/18.
 */

public class CDAdSampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CDAdsUtils.initialize(this);
//        CDAdsUtils.setGDPR(true);
//        CDAdsUtils.setConsent(true);
        CDAdsUtils.setLogLevel(Level.ALL);

        CDAdsUtils.setGeoIpLocationEnabled(false);
    }
}
