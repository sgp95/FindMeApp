package com.guillen.santiago.findmeapp.view.careTaker.patients;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.domain.Patient;

import io.reactivex.observers.DisposableObserver;

public class PatientListPresenter implements PatientListContract.Presenter{
    private Patient patientInteractor;
    private PatientListContract.View view;

    public PatientListPresenter(PatientListContract.View view) {
        this.patientInteractor = new Patient();
        this.view = view;
    }

    private DisposableObserver<ObservableModel<PatientModel>> createPatientsObserver(){
        return new DisposableObserver<ObservableModel<PatientModel>>() {
            @Override
            public void onNext(ObservableModel<PatientModel> patientModelObservableModel) {
                if(patientModelObservableModel.isAdded()){
                    view.onPatientAdded(patientModelObservableModel.getDataModel());
                }else if(patientModelObservableModel.isModified()){
                    view.onPatientModified(patientModelObservableModel.getDataModel());
                }else if(patientModelObservableModel.isRemoved()) {
                    view.onPatientRemoved(patientModelObservableModel.getDataModel());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RequestError", e.getMessage(), e.getCause());
                view.onRequestFailure();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void observeCareTakerPatients() {
        patientInteractor.observePatientsByCareTaker(createPatientsObserver());
    }

    @Override
    public void dispose() {
        patientInteractor.dispose();
    }
}
