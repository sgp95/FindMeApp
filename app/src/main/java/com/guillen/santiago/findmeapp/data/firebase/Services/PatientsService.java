package com.guillen.santiago.findmeapp.data.firebase.Services;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.guillen.santiago.findmeapp.FindMeApplication;
import com.guillen.santiago.findmeapp.data.firebase.ReferencesManager;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.data.model.User;

import javax.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class PatientsService {
    ReferencesManager manager;

    public PatientsService() {
        this.manager = new ReferencesManager();
    }

    public Single<String> savePatient(final PatientModel patient, final String careTakerId){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                manager.getPatientsCollection("", false)
                        .document(patient.getId())
                        .set(patient)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                manager.getPatientsCollection(careTakerId, true)
                                        .document(patient.getId())
                                        .set(patient)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                emitter.onSuccess(patient.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                emitter.onError(e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        });
            }
        });
    }

    public Single<String> savePatientByCareTaker(final PatientModel patient){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
            }
        });
    }

    public Observable<ObservableModel<PatientModel>> getPatientsByCareTaker(){
        return Observable.create(new ObservableOnSubscribe<ObservableModel<PatientModel>>() {
            @Override
            public void subscribe(final ObservableEmitter<ObservableModel<PatientModel>> emitter) throws Exception {
                 User currentUser = FindMeApplication.getInstance().getDatabase().userDao().getCurrentUser();
                manager
                        .getPatientsCollection(currentUser.getId(), true)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            emitter.onError(e);
                        }

                        assert queryDocumentSnapshots != null;
                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            ObservableModel<PatientModel> patientModel = new ObservableModel<>();
                            PatientModel patient = doc.getDocument().toObject(PatientModel.class);
                            patient.setId(doc.getDocument().getId());
                            patientModel.setDataModel(patient);
                            switch (doc.getType()){
                                case ADDED:
                                    patientModel.setAdded(true);
                                    emitter.onNext(patientModel);
                                    break;
                                case MODIFIED:
                                    patientModel.setModified(true);
                                    emitter.onNext(patientModel);
                                    break;
                                case REMOVED:
                                    patientModel.setRemoved(true);
                                    emitter.onNext(patientModel);
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }

    public Maybe<PatientModel> getPatient(final String patientId){
        return Maybe.create(new MaybeOnSubscribe<PatientModel>() {
            @Override
            public void subscribe(final MaybeEmitter<PatientModel> emitter) throws Exception {
                manager
                        .getPatientsCollection("", false)
                        .document(patientId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        PatientModel patient = documentSnapshot.toObject(PatientModel.class);
                        assert patient != null;
                        patient.setId(patientId);
                        emitter.onSuccess(patient);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
            }
        });
    }

    public Observable<ObservableModel<BeaconModel>> getPositionByPatient(final String patientId){
        return Observable.create(new ObservableOnSubscribe<ObservableModel<BeaconModel>>() {
            @Override
            public void subscribe(final ObservableEmitter<ObservableModel<BeaconModel>> emitter) throws Exception {
                manager
                        .getPatientPosition(patientId)
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

    public Completable updatePositionByPatient(final String patientId, final String beaconId, final Double newDistance){
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                manager.getPatientPosition(patientId)
                        .document(beaconId)
                        .update("patientDistance",newDistance)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        emitter.onComplete();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        emitter.onError(e);
                    }
                });
            }
        });
    }
}
