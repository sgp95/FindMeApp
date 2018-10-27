package com.guillen.santiago.findmeapp.domain;

import com.guillen.santiago.findmeapp.data.firebase.Services.BeaconsService;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Beacons extends BaseInteractor {
    private BeaconsService beaconsService;

    public Beacons() {
        super();
        beaconsService = new BeaconsService();
    }

    public void observeBeacons(DisposableObserver<ObservableModel<BeaconModel>> observer){
        Disposable disposable = beaconsService.getBeacons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        addDisposable(disposable);
    }
}
