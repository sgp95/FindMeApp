package com.guillen.santiago.findmeapp.view.patient;

public interface MainActivityContract {

    interface View{
        void onCurrentUserId(String id);
    }

    interface Presenter{
        void getCurrentUser();
    }
}
