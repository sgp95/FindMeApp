package com.guillen.santiago.findmeapp.view.login;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.CacheData;
import com.guillen.santiago.findmeapp.domain.Login;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class LoginPresenter implements LoginContract.Presenter{
    private Login loginInteractor;
    private CacheData cacheInteractor;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        loginInteractor = new Login();
        cacheInteractor = new CacheData();
        this.view = view;
    }

    private DisposableMaybeObserver<User> createLoginObserver(){
        return new DisposableMaybeObserver<User>() {
            @Override
            public void onSuccess(User user) {
                view.onLoginSuccess(user);
                this.dispose();
            }

            @Override
            public void onError(Throwable e) {
                view.onFailure((Exception) e);
                this.dispose();
            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
    }

    private DisposableMaybeObserver<PatientModel> createLoginAsPatientObserver(){
        return new DisposableMaybeObserver<PatientModel>() {
            @Override
            public void onSuccess(PatientModel patientModel) {
                view.onLoginPatientSuccess(patientModel);
                this.dispose();
            }

            @Override
            public void onError(Throwable e) {
                view.onFailure((Exception) e);
                this.dispose();
            }

            @Override
            public void onComplete() {

            }
        };
    }


    @Override
    public void loginUser(String email, String password, boolean asPatient) {
        if(asPatient){
            loginInteractor.loginAsPatient(email, password, createLoginAsPatientObserver());
        }else {
            loginInteractor.login(email, password, createLoginObserver());
        }
    }

    @Override
    public void setCurrentUser(User currentUser) {
        cacheInteractor.setCurrentUser(currentUser, new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d("rastro","Current user saved");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rastro","Current not saved, Failed");
            }
        });
    }

    @Override
    public void dispose() {
        loginInteractor.dispose();
    }
}
