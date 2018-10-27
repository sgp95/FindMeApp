package com.guillen.santiago.findmeapp.view.careTaker.beacons;

import com.guillen.santiago.findmeapp.data.model.BeaconModel;

public interface BeaconsContract {

    interface View {
        void onBeaconAdded(BeaconModel beacon);
        void onBeaconModified(BeaconModel beacon);
        void onBeaconRemoved(BeaconModel beacon);
        void onFailure();
    }

    interface Presenter{
        void getBeacons();

        void dispose();
    }
}
