package com.guillen.santiago.findmeapp.view.register;

import com.guillen.santiago.findmeapp.data.model.User;

public interface RegisterUserContract {

    interface View{
        void onRegisterSuccess(String userId);

        void onRegisterFailure();
    }

    interface Presenter{
        void registerUser(User user, String password);

        void dispose();
    }
}
