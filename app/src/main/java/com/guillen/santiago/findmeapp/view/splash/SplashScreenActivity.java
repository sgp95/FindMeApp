package com.guillen.santiago.findmeapp.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;
import com.guillen.santiago.findmeapp.view.login.LoginAcitivty;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        presenter = new SplashScreenPresenter(this );
        presenter.getCurrentUser();

    }

    private void initTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginAcitivty.class));
            }
        },2000);


    }

    @Override
    public void onUserLogged(boolean isPatient) {
        if(isPatient){
            startActivity(new Intent(SplashScreenActivity.this, com.guillen.santiago.findmeapp.view.patient.MainActivity.class));
        }else {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onUserNotFound() {
        initTimer();
    }
}
