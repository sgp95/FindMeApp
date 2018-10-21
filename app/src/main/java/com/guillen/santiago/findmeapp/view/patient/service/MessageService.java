package com.guillen.santiago.findmeapp.view.patient.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageService extends Service {

    public static final String PATIENT_ID = "patientId";
    public static final String BEACON_ID = "beaconId";
    public static final String BEACON_OBJECT = "beacon";
    public static final String BEACON_DISTANCE = "beaconDistance";

    private FirebaseFirestore firestore;

    @Override
    public void onCreate() {
        super.onCreate();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra(BEACON_ID));
            final String patientId = intent.getStringExtra(PATIENT_ID);
            Log.d("rastro", "patient id from SERVICE: "+patientId);
            firestore
                    .collection("Beacons")
                    .document(jsonObject.getString("id"))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    BeaconModel beacon = documentSnapshot.toObject(BeaconModel.class);
                    beacon.setId(documentSnapshot.getId());
                    Log.d("rastro", "patient id from Fist request: "+beacon.getId());
                    firestore
                            .collection("Patients")
                            .document(patientId)
                            .collection("Position")
                            .document(beacon.getId())
                            .set(beacon)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("rastro","Beacon Saved from in SERVICE");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("serviceFail",e.getMessage(),e.getCause());
                                }
                            });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return START_REDELIVER_INTENT ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Intent intent = new Intent("RESTART_SERVICE");
//        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
