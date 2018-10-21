package com.guillen.santiago.findmeapp.domain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillen.santiago.findmeapp.data.firebase.Services.PatientsService;
import com.guillen.santiago.findmeapp.data.firebase.Services.UserService;
import com.guillen.santiago.findmeapp.data.model.PatientModel;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Register extends BaseInteractor {

    private UserService userService;
    private PatientsService patientsService;

    public Register(){
        super();
        userService = new UserService();
        patientsService = new PatientsService();
    }

    public void registerPatient(final String careTakerId, final PatientModel patient, String email, String password, DisposableSingleObserver<String> observer){
        Disposable disposable = userService
                .RegisterUser(email, password)
                .flatMap(new Function<Task<AuthResult>, Single<String>>() {
                    @Override
                    public Single<String> apply(Task<AuthResult> authResultTask) throws Exception {
                        patient.setId(authResultTask.getResult().getUser().getUid());
                        return patientsService.savePatient(patient, careTakerId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        addDisposable(disposable);
    }
}
