package com.guillen.santiago.findmeapp.view.patient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.guillen.santiago.findmeapp.view.patient.service.MessageService;

public class BeaconMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Nearby.getMessagesClient(context).handleIntent(intent, new MessageListener(){
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d("rastro", "Message from receiver : " + new String(message.getContent()));
                Intent newIntent = new Intent(context, MessageService.class);
                newIntent.putExtra(MessageService.PATIENT_ID, intent.getStringExtra(MessageService.PATIENT_ID));
                newIntent.putExtra(MessageService.BEACON_ID, new String(message.getContent()));

                context.startService(newIntent);
            }


            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Log.d("rastro", "Message Lost in Background ");
            }
        });
    }
}
