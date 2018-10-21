package com.guillen.santiago.findmeapp.view.careTaker;

import com.guillen.santiago.findmeapp.data.model.User;

public interface MainContract {

    interface View{

        void onCurrentUserLoaded(User currentUser);

        void onCurrentUserFailed();

        void onLogout();
    }

    interface Presenter{

        void getCurrentUser();

        void logout();
    }
}
