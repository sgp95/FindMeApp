package com.guillen.santiago.findmeapp.view.careTaker.patients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;
import com.guillen.santiago.findmeapp.view.careTaker.patientDetail.PatientDetailActivity;
import com.guillen.santiago.findmeapp.view.register.RegisterUserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PatientListFragment extends Fragment implements PatientListContract.View, PatientAdapter.AdapterListener {

    @BindView(R.id.rvPatients)
    protected RecyclerView rvPatients;

    private MainActivity mainActivity;
    private PatientListContract.Presenter presenter;
    private PatientAdapter adapter;
    private String currentUserId;

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

        Bundle bundle = getArguments();
        currentUserId = bundle.getString(MainActivity.CURRENT_USER_ID);

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
        adapter.addPatient(patient);
    }

    @Override
    public void onPatientModified(PatientModel patient) {
        adapter.modifyPatient(patient);
    }

    @Override
    public void onPatientRemoved(PatientModel patient) {
        adapter.removePatient(patient);
    }

    @Override
    public void onRequestFailure() {

    }

    @Override
    public void onClick(PatientModel patient) {
        Gson gson = new Gson();
        Intent intent = new Intent(mainActivity, PatientDetailActivity.class);
        intent.putExtra(PatientDetailActivity.PATIENT_INFO, gson.toJson(patient));
        startActivity(intent);
    }

    @OnClick(R.id.ftBtnAddPatient)
    public void onNewPatientButtonClicked(){
        Intent intent = new Intent(mainActivity, RegisterUserActivity.class);
        intent.putExtra(MainActivity.CURRENT_USER_ID,currentUserId);
        startActivityForResult(intent, RegisterUserActivity.INTENT_CODE);
    }
    private void setUpPatientAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPatients.setLayoutManager(layoutManager);
        adapter = new PatientAdapter(this);
        rvPatients.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == RegisterUserActivity.INTENT_CODE){
//            if(resultCode == getActivity().RESULT_OK){
//            }else if(resultCode == getActivity().RESULT_CANCELED) {
//
//            }
//        }
    }
}
