package com.guillen.santiago.findmeapp.view.careTaker.patientDetail;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.domain.Patient;

import io.reactivex.observers.DisposableObserver;

public class PatientDetailPresenter implements PatientDetailContract.Presenter {

    private Patient patientInteractor;
    private PatientDetailContract.View view;

    public PatientDetailPresenter(PatientDetailContract.View view) {
        patientInteractor = new Patient();
        this.view = view;
    }

    private DisposableObserver<ObservableModel<BeaconModel>> createPositionObserver(){
        return new DisposableObserver<ObservableModel<BeaconModel>>() {
            @Override
            public void onNext(ObservableModel<BeaconModel> beaconModelObservableModel) {
                if(beaconModelObservableModel.isAdded()){
                    view.onPositionAdded(beaconModelObservableModel.getDataModel());
                }else if(beaconModelObservableModel.isModified()){
                    view.onPositionModified(beaconModelObservableModel.getDataModel());
                }else if(beaconModelObservableModel.isRemoved()) {
                    view.onPositionRemoved(beaconModelObservableModel.getDataModel());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RequestError", e.getMessage(), e.getCause());
                view.onFailure();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void observePostion(String patientId) {
        patientInteractor.observePatientPostiiton(patientId, createPositionObserver());
    }

    @Override
    public void dispose() {
        patientInteractor.dispose();
    }
}
