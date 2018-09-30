package com.guillen.santiago.findmeapp.view.register;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.Register;

import io.reactivex.observers.DisposableSingleObserver;

public class RegisterUserPresenter implements RegisterUserContract.Presenter {
    private Register registerInteractor;
    private RegisterUserContract.View view;

    public RegisterUserPresenter(RegisterUserContract.View view) {
        registerInteractor = new Register();
        this.view = view;
    }

    private DisposableSingleObserver<String> createRegisterObserver(){
        return new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String userId) {
                Log.e("rasto", userId+" success");
                view.onRegisterSuccess(userId);
                this.dispose();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RagisterFirebase", e.getMessage(), e.getCause());
                view.onRegisterFailure();
                this.dispose();
            }
        };
    }

    @Override
    public void registerUser(User user, String password) {
        registerInteractor.registerUser(user, password, createRegisterObserver());
    }

    @Override
    public void dispose() {
        registerInteractor.dispose();
    }
}
