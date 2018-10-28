package com.guillen.santiago.findmeapp.view.patient;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.BleSignal;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.patient.receiver.BeaconMessageReceiver;
import com.guillen.santiago.findmeapp.view.patient.service.MessageService;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    private MessageListener messageListener;
    private Distance newDistance;

    private SubscribeOptions options;
    private MainActivityContract.Presenter presenter;
    private String currentUserId;

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
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    public void onCurrentUserId(String id) {

        currentUserId = id;

        Nearby.getMessagesClient(this).subscribe(createIntent(id),options);

        setUpNearbyBeaconsListener();
        Nearby.getMessagesClient(this).subscribe(messageListener, options);

    }

    @Override
    public void onLogout() {
        System.exit(0);
    }

    private void setUpNearbyBeaconsListener(){
        messageListener = new MessageListener(){
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d("rastro", "Found message Foreground: " + new String(message.getContent()));

            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Log.d("rastro", "Lost sight of message Foreground: " + new String(message.getContent()));
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                super.onDistanceChanged(message, distance);
                try {
                    Double newDistance = Double.valueOf(String.format("%.2f", distance.getMeters()));
                    Log.d("rastro", "New distance Foreground: " + newDistance);
                    JSONObject jsonObject = new JSONObject(new String(message.getContent()));
                    String beaconId = jsonObject.getString("id");
                    presenter.updatePatientPosition(currentUserId, beaconId, newDistance);

                } catch (JSONException e) {
                    Log.e("rastro", "Could not parse malformed JSON");
                }
            }

            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                super.onBleSignalChanged(message, bleSignal);
            }
        };

    }

    @OnClick(R.id.btnLogout)
    public void onLogoutButton(){
        presenter.logout();
    }

    private PendingIntent createIntent(String patientId){
        Intent intent = new Intent(this, BeaconMessageReceiver.class);
        intent.putExtra(MessageService.PATIENT_ID, patientId);

        return PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
