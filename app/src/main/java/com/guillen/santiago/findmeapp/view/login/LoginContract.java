package com.guillen.santiago.findmeapp.view.login;

public interface LoginContract {

    interface View {

        void onLoginSuccess();

        void onFailure(Exception e);
    }

    interface Presenter{

        void loginUser(String email, String password);

        void dispose();
    }
}
