package com.guillen.santiago.findmeapp.domain;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.guillen.santiago.findmeapp.data.firebase.Services.UserService;
import com.guillen.santiago.findmeapp.data.model.User;

import io.reactivex.MaybeSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class Login extends BaseInteractor {
    private UserService userService;

    public Login() {
        super();
        userService = new UserService();
    }

    public void login(String email, String password, DisposableMaybeObserver<User> observer) {
        Disposable disposable = userService
                .loginUser(email, password)
                .flatMap(new Function<Task<AuthResult>, MaybeSource<User>>() {
            @Override
            public MaybeSource<User> apply(Task<AuthResult> authResultTask) throws Exception {
                return userService.getUser(authResultTask.getResult().getUser().getUid());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        addDisposable(disposable);
    }

}
