package com.guillen.santiago.findmeapp.data.firebase.Services;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.guillen.santiago.findmeapp.FindMeApplication;
import com.guillen.santiago.findmeapp.data.firebase.ReferencesManager;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.data.model.User;

import javax.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class PatientsService {
    ReferencesManager manager;

    public PatientsService() {
        this.manager = new ReferencesManager();
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
}
