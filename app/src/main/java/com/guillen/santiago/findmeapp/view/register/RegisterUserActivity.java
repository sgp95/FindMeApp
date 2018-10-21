package com.guillen.santiago.findmeapp.view.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.view.Utils;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserContract.View {
    @BindView(R.id.etUserMail)
    protected EditText etUserMail;
    @BindView(R.id.etUserPassword)
    protected EditText etUserPassword;
    @BindView(R.id.etUserFirstName)
    protected EditText etUserFirstName;
    @BindView(R.id.etUserLastName)
    protected EditText etUserLastName;
    @BindView(R.id.etDocumentNum)
    protected EditText etDocumentNum;
    @BindView(R.id.etAge)
    protected EditText etAge;
    @BindView(R.id.etSex)
    protected EditText etSex;
    @BindView(R.id.spSickness)
    protected Spinner spSickness;
    @BindView(R.id.spSicknessLevel)
    protected Spinner spSicknessLevel;
    @BindView(R.id.btnRegisterUser)
    protected Button btnRegisterUser;
    @BindView(R.id.pbRegisterUser)
    protected ProgressBar pbRegisterUser;

    private RegisterUserPresenter presenter;
    private PatientModel patient;
    private String currentUserId;
    public static final int INTENT_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);

        currentUserId = getIntent().getStringExtra(MainActivity.CURRENT_USER_ID);

        setUpView();

        setUserTypeListener();
    }

    private void setUpView(){
        setTitle("Registrar paciente");
        presenter = new RegisterUserPresenter(this);

        patient = new PatientModel();
    }

    private void setUserTypeListener(){
        spSickness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sickness = (String)adapterView.getItemAtPosition(i);
                patient.setSickness(sickness);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });

        spSicknessLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sicknessLevel = (String)adapterView.getItemAtPosition(i);
                patient.setSicknessLevel(sicknessLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    protected int generatePatientNumber(){
        return new Random().nextInt(1000);
    }

    @OnClick(R.id.btnRegisterUser)
    public void RegisterUser(){
        Utils.setLoadingView(pbRegisterUser, true);
        patient.setName(etUserFirstName.getText().toString().trim());
        patient.setSurname(etUserLastName.getText().toString().trim());
        patient.setAge(Integer.parseInt(etAge.getText().toString().trim()));
        patient.setSex(etSex.getText().toString().trim());
        patient.setDocumentNumber(Integer.parseInt(etDocumentNum.getText().toString().trim()));
        patient.setNumber(generatePatientNumber());

        presenter.registerPatient(
                etUserMail.getText().toString().trim(),
                etUserPassword.getText().toString().trim(),
                patient,
                currentUserId);
    }

    @Override
    public void onRegisterSuccess(String userId) {
        goBackToPatientList(RESULT_OK);
    }

    @Override
    public void onRegisterFailure() {
        goBackToPatientList(RESULT_CANCELED);
    }

    private void goBackToPatientList(int resultCode){
        setResult(resultCode);
        finish();
    }
}
