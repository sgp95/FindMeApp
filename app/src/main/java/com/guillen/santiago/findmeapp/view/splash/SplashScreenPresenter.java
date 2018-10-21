package com.guillen.santiago.findmeapp.view.splash;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.CacheData;
import com.guillen.santiago.findmeapp.view.model.UserType;

import io.reactivex.observers.DisposableMaybeObserver;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    private SplashScreenContract.View view;
    private CacheData cacheInteractor;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.view = view;
        cacheInteractor = new CacheData();
    }

    @Override
    public void getCurrentUser() {
        cacheInteractor.getCurrentUser(new DisposableMaybeObserver<User>() {
            @Override
            public void onSuccess(User user) {
                view.onUserLogged(user.getType().equals(UserType.PATIENT.getValue()));
            }

            @Override
            public void onError(Throwable e) {
                view.onUserNotFound();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
