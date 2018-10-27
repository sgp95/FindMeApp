package com.guillen.santiago.findmeapp.data.firebase.Services;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.guillen.santiago.findmeapp.data.firebase.ReferencesManager;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class BeaconsService {
    ReferencesManager manager;

    public BeaconsService() {
        manager = new ReferencesManager();
    }

    public Observable<ObservableModel<BeaconModel>> getBeacons(){
        return Observable.create(new ObservableOnSubscribe<ObservableModel<BeaconModel>>() {
            @Override
            public void subscribe(final ObservableEmitter<ObservableModel<BeaconModel>> emitter) throws Exception {
                manager
                        .getBeaconsCollection()
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            emitter.onError(e);
                        }

                        assert queryDocumentSnapshots != null;
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            ObservableModel<BeaconModel> beaconModel = new ObservableModel<>();
                            BeaconModel beacon = doc.getDocument().toObject(BeaconModel.class);
                            beacon.setId(doc.getDocument().getId());
                            beaconModel.setDataModel(beacon);
                            switch (doc.getType()){
                                case ADDED:
                                    beaconModel.setAdded(true);
                                    emitter.onNext(beaconModel);
                                    break;
                                case MODIFIED:
                                    beaconModel.setModified(true);
                                    emitter.onNext(beaconModel);
                                    break;
                                case REMOVED:
                                    beaconModel.setRemoved(true);
                                    emitter.onNext(beaconModel);
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }
}
