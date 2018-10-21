package com.guillen.santiago.findmeapp.view.patient;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.patient.receiver.BeaconMessageReceiver;
import com.guillen.santiago.findmeapp.view.patient.service.MessageService;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private MessageListener messageListener;
    private Distance newDistance;

    private SubscribeOptions options;
    private MainActivityContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);

        presenter = new MainActivityPresenter(this);

        options = new SubscribeOptions
                .Builder()
                .setStrategy(Strategy.BLE_ONLY)
                .build();

        presenter.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Nearby.getMessagesClient(this).subscribe(messageListener);
    }

    @Override
    protected void onStop() {
//        Nearby.getMessagesClient(this).unsubscribe(messageListener);
        super.onStop();
    }

    @Override
    public void onCurrentUserId(String id) {

        //Google way
        Nearby.getMessagesClient(this).subscribe(createIntent(id),options);

        //Other way
//        Nearby.getMessagesClient(this).subscribe(createIntentService(id),options);
    }

    private PendingIntent createIntent(String patientId){
        Intent intent = new Intent(this, BeaconMessageReceiver.class);
        intent.putExtra(MessageService.PATIENT_ID, patientId);

        return PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
