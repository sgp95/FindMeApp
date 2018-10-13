package com.guillen.santiago.findmeapp.view.careTaker;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.CacheData;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class MainPresenter implements MainContract.Presenter {
    private CacheData cacheInteractor;
    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.cacheInteractor = new CacheData();
    }

    @Override
    public void getCurrentUser() {
        cacheInteractor.getCurrentUser(new DisposableMaybeObserver<User>() {
            @Override
            public void onSuccess(User user) {
                view.onCurrentUserLoaded(user);
            }

            @Override
            public void onError(Throwable e) {
                view.onCurrentUserFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void logout() {
        cacheInteractor.logout(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d("rastro","Logout success");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rastro","Logout failure");
            }
        });
    }
}
