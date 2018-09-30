package com.guillen.santiago.findmeapp.view.login;

import com.guillen.santiago.findmeapp.data.model.User;

public interface LoginContract {

    interface View {

        void onLoginSuccess(User userInfo);

        void onFailure(Exception e);
    }

    interface Presenter{

        void loginUser(String email, String password);

        void dispose();
    }
}
