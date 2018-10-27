package com.guillen.santiago.findmeapp.view.patient;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.CacheData;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private CacheData cacheInteractor;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        cacheInteractor = new CacheData();
    }

    @Override
    public void getCurrentUser() {
        cacheInteractor.getCurrentUser(new DisposableMaybeObserver<User>() {
            @Override
            public void onSuccess(User user) {
                view.onCurrentUserId(user.getId());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("CurrentUserFailed",e.getMessage(),e.getCause());
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
                view.onLogout();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("rastro","Logout failure");
            }
        });
    }
}
