package com.guillen.santiago.findmeapp.view.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillen.santiago.findmeapp.domain.Login;

import io.reactivex.observers.DisposableMaybeObserver;

public class LoginPresenter implements LoginContract.Presenter{
    private Login loginInteractor;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        loginInteractor = new Login();
        this.view = view;
    }

    private DisposableMaybeObserver<Task<AuthResult>> createLoginObserver(){
        return new DisposableMaybeObserver<Task<AuthResult>>() {
            @Override
            public void onSuccess(Task<AuthResult> authResultTask) {
                view.onLoginSuccess();
                this.dispose();
            }

            @Override
            public void onError(Throwable e) {
                view.onFailure((Exception)e);
                this.dispose();
            }

            @Override
            public void onComplete() {
                this.dispose();
            }
        };
    }


    @Override
    public void loginUser(String email, String password) {
        loginInteractor.login(email, password, createLoginObserver());
    }

    @Override
    public void dispose() {
        loginInteractor.dispose();
    }
}
