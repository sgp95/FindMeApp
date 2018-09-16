package com.guillen.santiago.findmeapp.view.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.BleSignal;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.guillen.santiago.findmeapp.R;

public class MainActivity extends AppCompatActivity {
    private MessageListener messageListener;
    private Message message;
    private Distance newDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);

        messageListener = new MessageListener(){
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d("rastro", "Found message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Log.d("rastro", "Lost sight of message: " + new String(message.getContent()));
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                super.onDistanceChanged(message, distance);
                Log.d("rastro", "New distance: " + distance.getMeters());
                newDistance = distance;
                Log.d("rastro", "Distance changed by: " + distance.compareTo(newDistance));
            }

            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                super.onBleSignalChanged(message, bleSignal);
            }
        };

        message = new Message("Hello World".getBytes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Nearby.getMessagesClient(this).publish(message);
        Nearby.getMessagesClient(this).subscribe(messageListener);
    }

    @Override
    protected void onStop() {
        Nearby.getMessagesClient(this).unpublish(message);
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
        super.onStop();
    }
}
