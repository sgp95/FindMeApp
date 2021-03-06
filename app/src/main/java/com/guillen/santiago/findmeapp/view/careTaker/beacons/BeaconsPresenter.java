package com.guillen.santiago.findmeapp.view.careTaker.beacons;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.domain.Beacons;

import io.reactivex.observers.DisposableObserver;

public class BeaconsPresenter implements BeaconsContract.Presenter {

    private Beacons beaconsInteractor;
    private BeaconsContract.View view;

    public BeaconsPresenter(BeaconsContract.View view) {
        beaconsInteractor = new Beacons();
        this.view = view;
    }

    private DisposableObserver<ObservableModel<BeaconModel>> createBeaconsObserver(){
        return new DisposableObserver<ObservableModel<BeaconModel>>() {
            @Override
            public void onNext(ObservableModel<BeaconModel> beaconModelObservableModel) {
                if(beaconModelObservableModel.isAdded()){
                    view.onBeaconAdded(beaconModelObservableModel.getDataModel());
                }else if(beaconModelObservableModel.isModified()){
                    view.onBeaconModified(beaconModelObservableModel.getDataModel());
                }else if(beaconModelObservableModel.isRemoved()) {
                    view.onBeaconRemoved(beaconModelObservableModel.getDataModel());
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
    public void getBeacons() {
        beaconsInteractor.observeBeacons(createBeaconsObserver());
    }

    @Override
    public void dispose() {
        beaconsInteractor.dispose();
    }
}
