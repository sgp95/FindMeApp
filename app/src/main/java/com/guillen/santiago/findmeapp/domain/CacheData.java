package com.guillen.santiago.findmeapp.domain;

import com.guillen.santiago.findmeapp.FindMeApplication;
import com.guillen.santiago.findmeapp.data.cache.FindMeAppDatabase;
import com.guillen.santiago.findmeapp.data.model.User;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class CacheData extends BaseInteractor {
    private FindMeAppDatabase database;

    public CacheData() {
        super();
        this.database = FindMeApplication.getInstance().getDatabase();
    }

    public void setCurrentUser(final User user, DisposableCompletableObserver observer){
        Disposable disposable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    database.userDao().logout();
                    database.userDao().insert(user);
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        addDisposable(disposable);
    }

    public void getCurrentUser(DisposableMaybeObserver<User> observer){
        Disposable disposable = Maybe.create(new MaybeOnSubscribe<User>() {
            @Override
            public void subscribe(MaybeEmitter<User> emitter) throws Exception {
                try {
                    User user = database.userDao().getCurrentUser();
                    emitter.onSuccess(user);
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        addDisposable(disposable);
    }

    public void logout(DisposableCompletableObserver observer){
        Disposable disposable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                try {
                    database.userDao().logout();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        addDisposable(disposable);
    }
}
