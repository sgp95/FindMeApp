package com.guillen.santiago.findmeapp.view.careTaker.patientDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.BeaconModel;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.view.careTaker.beacons.BeaconAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientDetailActivity extends AppCompatActivity implements PatientDetailContract.View, BeaconAdapter.AdapterListener {

    public static final String PATIENT_INFO = "patient";

    @BindView(R.id.tvFullName)
    protected TextView tvFullName;
    @BindView(R.id.tvNumber)
    protected TextView tvNumber;
    @BindView(R.id.tvAge)
    protected TextView tvAge;
    @BindView(R.id.tvSex)
    protected TextView tvSex;
    @BindView(R.id.tvDni)
    protected TextView tvDni;
    @BindView(R.id.tvSickness)
    protected TextView tvSickness;
    @BindView(R.id.tvSicknessLevel)
    protected TextView tvSicknessLevel;

    @BindView(R.id.rvPatientPosition)
    protected RecyclerView rvPatientPosition;

    private PatientModel patient;
    private PatientDetailContract.Presenter presenter;
    private BeaconAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        presenter = new PatientDetailPresenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPatientPosition.setLayoutManager(layoutManager);
        adapter = new BeaconAdapter(this);
        adapter.setForPosition(true);
        rvPatientPosition.setAdapter(adapter);

        String patientFromJson = getIntent().getStringExtra(PATIENT_INFO);
        patient = gson.fromJson(patientFromJson, PatientModel.class);

        setPatientData(patient);
        presenter.observePostion(patient.getId());

    }

    private void setPatientData(PatientModel patientData) {
        tvFullName.setText(patientData.getSurname() + ", " + patientData.getName());
        tvNumber.setText(patientData.getNumber() + "");
        tvAge.setText("Edad: " + patientData.getAge());
        tvSex.setText("Sexo: " + patientData.getSex());
        tvDni.setText("DNI: " + patientData.getDocumentNumber());
        tvSickness.setText("Enfermedad/Condición: " + patientData.getSickness());
        tvSicknessLevel.setText("Estado: " + patientData.getSicknessLevel());
    }

    @Override
    public void onPositionAdded(BeaconModel beacon) {
        Log.d("rastro","onPositionAdded");
        adapter.addBeacon(beacon);
    }

    @Override
    public void onPositionModified(BeaconModel beacon) {
        Log.d("rastro", "onPositionModified");
        adapter.modifyBeacon(beacon);
    }

    @Override
    public void onPositionRemoved(BeaconModel beacon) {
        Log.d("rastro","onPositionRemoved");
        adapter.removeBeacon(beacon);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onClick(BeaconModel beacon) {

    }
}
