package com.guillen.santiago.findmeapp.domain;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseInteractor {
    private CompositeDisposable disposables;

    /**
     * constructor to be called in the constructor of the interactor implementation
     *
     */
    public BaseInteractor() {
        this.disposables = new CompositeDisposable();
    }

    /**
     * dispose of all disposables when activity is destroy
     *
     */
    public void dispose(){
        if(disposables != null && !disposables.isDisposed()){
            disposables.clear();
            //beware: clear() or dispose()
            // clear() -> clear all and accept new disposables
            // dispose() -> clear all and set isDisposed() to true and will not accept new disposables
        }
    }

    /**
     * Add disposable to Composite of Disposables
     *
     * @param disposable to be added
     */
    public void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }
}
