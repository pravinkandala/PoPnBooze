package edu.niu.cs.z1761257.popnbooze;

import android.app.Application;


/**
 * Created by Pravin on 5/7/16.
 * Project: Gathr
 */
public class ParseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
       com.parse.Parse.enableLocalDatastore(this);
        com.parse.Parse.initialize(this, getString(R.string.YOUR_APPLICATION_ID), getString(R.string.YOUR_CLIENT_KEY));
    }
}
