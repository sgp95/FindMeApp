package com.guillen.santiago.findmeapp.view.patient;

public interface MainActivityContract {

    interface View{
        void onCurrentUserId(String id);

        void onLogout();
    }

    interface Presenter{
        void getCurrentUser();

        void logout();
    }
}
