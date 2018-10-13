package com.guillen.santiago.findmeapp.view.careTaker.patients;

import com.guillen.santiago.findmeapp.data.model.PatientModel;

public interface PatientListContract {
    interface View {
        void onPatientAdded(PatientModel patient);

        void onPatientModified(PatientModel patient);

        void onPatientRemoved(PatientModel patient);

        void onRequestFailure();
    }

    interface Presenter {
        void observeCareTakerPatients();

        void dispose();
    }
}
