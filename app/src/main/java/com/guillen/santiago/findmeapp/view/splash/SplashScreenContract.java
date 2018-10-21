package com.guillen.santiago.findmeapp.view.splash;

public interface SplashScreenContract {

    interface View{
        void onUserLogged(boolean isPatient);

        void onUserNotFound();
    }

    interface Presenter{
        void getCurrentUser();
    }
}
