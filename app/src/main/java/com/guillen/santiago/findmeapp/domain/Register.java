package com.guillen.santiago.findmeapp.domain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillen.santiago.findmeapp.data.firebase.Services.UserService;
import com.guillen.santiago.findmeapp.data.model.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Register extends BaseInteractor {

    private UserService userService;

    public Register(){
        super();
        userService = new UserService();
    }

    public void registerUser(final User user, String password, DisposableSingleObserver<String> observer){
        Disposable disposable = userService
                .RegisterUser(user.getEmail(), password)
                .map(new Function<Task<AuthResult>, String>() {
                    @Override
                    public String apply(Task<AuthResult> authResultTask) {
                        userService.saveUserData(user, authResultTask.getResult().getUser().getUid());
                        return authResultTask.getResult().getUser().getUid();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        addDisposable(disposable);
    }
}
