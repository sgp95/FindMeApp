package com.guillen.santiago.findmeapp.view.login;

import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.data.model.User;

public interface LoginContract {

    interface View {

        void onLoginSuccess(User userInfo);

        void onLoginPatientSuccess(PatientModel patient);

        void onFailure(Exception e);
    }

    interface Presenter{

        void loginUser(String email, String password, boolean asPatient);

        void setCurrentUser(User currentUser);

        void dispose();
    }
}
