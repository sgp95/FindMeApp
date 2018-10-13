package com.guillen.santiago.findmeapp;

import android.app.Application;

import com.guillen.santiago.findmeapp.data.cache.FindMeAppDatabase;

public class FindMeApplication extends Application{

    private static FindMeApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public FindMeAppDatabase getDatabase(){
        return FindMeAppDatabase.getDatabase(this);
    }

    public static FindMeApplication getInstance(){
        return instance;
    }
}
