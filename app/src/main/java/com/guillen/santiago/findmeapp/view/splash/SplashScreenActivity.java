package com.guillen.santiago.findmeapp.view.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.login.LoginAcitivty;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initTimer();

    }

    private void initTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                goToNexActivity();
            }
        },2000);


    }

    private void goToNexActivity(){
        startActivity(new Intent(SplashScreenActivity.this, LoginAcitivty.class));
    }
}
