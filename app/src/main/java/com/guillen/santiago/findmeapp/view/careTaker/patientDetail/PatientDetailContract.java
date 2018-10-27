package com.guillen.santiago.findmeapp.view.careTaker.patientDetail;

import com.guillen.santiago.findmeapp.data.model.BeaconModel;

public interface PatientDetailContract {
    interface View{
        void onPositionAdded(BeaconModel beacon);
        void onPositionModified(BeaconModel beacon);
        void onPositionRemoved(BeaconModel beacon);
        void onFailure();
    }

    interface Presenter{
        void observePostion(String patientId);

        void  dispose();
    }
}
