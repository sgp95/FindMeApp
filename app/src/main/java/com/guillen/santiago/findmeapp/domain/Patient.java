package com.guillen.santiago.findmeapp.domain;

import com.guillen.santiago.findmeapp.data.firebase.Services.PatientsService;
import com.guillen.santiago.findmeapp.data.model.ObservableModel;
import com.guillen.santiago.findmeapp.data.model.PatientModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Patient extends BaseInteractor {
    private PatientsService patientsService;

    public Patient() {
        super();
        this.patientsService = new PatientsService();
    }

    public void observePatientsByCareTaker(DisposableObserver<ObservableModel<PatientModel>> observer){
        Disposable disposable = patientsService.getPatientsByCareTaker()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        addDisposable(disposable);
    }
}
