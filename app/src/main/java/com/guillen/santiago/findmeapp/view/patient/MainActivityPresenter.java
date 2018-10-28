package com.guillen.santiago.findmeapp.view.patient;

import android.util.Log;

import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.domain.CacheData;
import com.guillen.santiago.findmeapp.domain.Patient;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private CacheData cacheInteractor;
    private Patient patientInteractor;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
        cacheInteractor = new CacheData();
        patientInteractor = new Patient();
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
    public void updatePatientPosition(String userId, String beaconId, Double newDistance) {
        patientInteractor.updatePatientPostiiton(userId, beaconId, newDistance, new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Log.d("rastro", "Distance Updated");
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(e.getLocalizedMessage(), e.getMessage(), e.getCause());
                dispose();
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

    @Override
    public void dispose() {
        patientInteractor.dispose();
    }
}
