package com.guillen.santiago.findmeapp.view.careTaker.beacons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BeaconsFragment extends Fragment implements BeaconsContract.View, BeaconAdapter.AdapterListener {

    public static final String TAG = "LIST_BEACONS_FRAGMENT";

    @BindView(R.id.rvBeacons)
    protected RecyclerView rvBeacons;

    private MainActivity mainActivity;
    private BeaconsContract.Presenter presenter;
    private BeaconAdapter adapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        mainActivity.setBarTitle("Beacons");

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

        setUpBeaconAdapter();
        presenter = new BeaconsPresenter(this);
        presenter.getBeacons();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    private void setUpBeaconAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvBeacons.setLayoutManager(layoutManager);
        adapter = new BeaconAdapter(this);
        rvBeacons.setAdapter(adapter);
    }

    @Override
    public void onBeaconAdded(BeaconModel beacon) {
        adapter.addBeacon(beacon);
    }

    @Override
    public void onBeaconModified(BeaconModel beacon) {
        adapter.modifyBeacon(beacon);
    }

    @Override
    public void onBeaconRemoved(BeaconModel beacon) {
        adapter.removeBeacon(beacon);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onClick(BeaconModel beacon) {
        //TODO go to detail beacon
    }
}
