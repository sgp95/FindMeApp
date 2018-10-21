package com.guillen.santiago.findmeapp.view.careTaker.beacons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.BleSignal;
import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BeaconsFragment extends Fragment implements BeaconsContract.View {

    public static final String TAG = "LIST_BEACONS_FRAGMENT";

    @BindView(R.id.tv_beacon_status)
    protected TextView tvBeaconStatus;
    @BindView(R.id.tv_beacon_room)
    protected TextView tvBeaconRoom;
    @BindView(R.id.tv_beacon_type)
    protected TextView tvBeaconType;
    @BindView(R.id.tv_beacon_distance)
    protected TextView tvBeaconDistance;

    private MainActivity mainActivity;
    private MessageListener messageListener;
    private Message message;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        mainActivity.setBarTitle("Beacons");

        setUpNearbyBeaconsListener(); // TODO enable when ready to work with beacons
    }

    @Override
    public void onStart() {
        super.onStart();
        Nearby.getMessagesClient(mainActivity).publish(message);
        Nearby.getMessagesClient(mainActivity).subscribe(messageListener); //TODO enable when ready to work with beacons
    }

    @Override
    public void onStop() {
        Nearby.getMessagesClient(mainActivity).unpublish(message);
        Nearby.getMessagesClient(mainActivity).unsubscribe(messageListener); //TODO enable when ready to work with beacons
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beacon_list, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setUpNearbyBeaconsListener(); // TODO enable when ready to work with beacons
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void setUpNearbyBeaconsListener(){
        messageListener = new MessageListener(){
            @Override
            public void onFound(Message message) {
                super.onFound(message);
                Log.d("rastro", "Found message: " + new String(message.getContent()));

//                try {
//                    JSONObject jsonObject = new JSONObject(new String(message.getContent()));
//                    BeaconAttachment beaconAttachment = BeaconAttachment.fromJson(jsonObject);
//                    tvBeaconStatus.setText("Found");
//                    tvBeaconRoom.setText(beaconAttachment.getRoom());
//                    tvBeaconType.setText(beaconAttachment.getType());
//
//                } catch (JSONException e) {
//                    Log.e("rastro", "Could not parse malformed JSON");
//                }

            }

            @Override
            public void onLost(Message message) {
                super.onLost(message);
                Log.d("rastro", "Lost sight of message: " + new String(message.getContent()));
                tvBeaconStatus.setText("Lost");
            }

            @Override
            public void onDistanceChanged(Message message, Distance distance) {
                super.onDistanceChanged(message, distance);
                Log.d("rastro", "New distance: " + distance.getMeters());
                tvBeaconDistance.setText(distance.getMeters()+" metros");
            }

            @Override
            public void onBleSignalChanged(Message message, BleSignal bleSignal) {
                super.onBleSignalChanged(message, bleSignal);
            }
        };

        message = new Message("Hello World".getBytes());

    }
}
