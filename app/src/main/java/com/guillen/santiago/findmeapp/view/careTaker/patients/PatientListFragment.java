package com.guillen.santiago.findmeapp.view.careTaker.patients;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PatientListFragment extends Fragment implements PatientListContract.View, PatientAdapter.AdapterListener {

    @BindView(R.id.ftBtnAddPatient)
    protected FloatingActionButton ftBtnAddPatient;
    @BindView(R.id.rvPatients)
    protected RecyclerView rvPatients;

    private MainActivity mainActivity;
    private PatientListContract.Presenter presenter;
    private PatientAdapter adapter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        mainActivity.setBarTitle("Pacientes");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients_list, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpPatientAdapter();
        presenter = new PatientListPresenter(this);
        presenter.observeCareTakerPatients();
    }

    @Override
    public void onDestroyView() {
        presenter.dispose();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onPatientAdded(PatientModel patient) {
        Log.d("rastro","Patient added id: "+patient.getId()+" name: "+patient.getName());
        adapter.addPatient(patient);
    }

    @Override
    public void onPatientModified(PatientModel patient) {
        Log.d("rastro","Patient modified id: "+patient.getId()+" name: "+patient.getName());
        adapter.modifyPatient(patient);
    }

    @Override
    public void onPatientRemoved(PatientModel patient) {
        Log.d("rastro","Patient removed id: "+patient.getId()+" name: "+patient.getName());
        adapter.removePatient(patient);
    }

    @Override
    public void onRequestFailure() {

    }

    @Override
    public void onClick(PatientModel patient) {
        Log.d("rastro","Patient clicked ");
    }

    private void setUpPatientAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPatients.setLayoutManager(layoutManager);
        adapter = new PatientAdapter(this);
        rvPatients.setAdapter(adapter);
    }
}
