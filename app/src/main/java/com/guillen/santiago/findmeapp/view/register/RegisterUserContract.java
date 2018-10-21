package com.guillen.santiago.findmeapp.view.register;

import com.guillen.santiago.findmeapp.data.model.PatientModel;

public interface RegisterUserContract {

    interface View{
        void onRegisterSuccess(String userId);

        void onRegisterFailure();
    }

    interface Presenter{
        void registerPatient(String email, String password, PatientModel patient, String careTakerId);

        void dispose();
    }
}
