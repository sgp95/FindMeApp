package com.guillen.santiago.findmeapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.guillen.santiago.findmeapp.R;
import com.guillen.santiago.findmeapp.data.model.PatientModel;
import com.guillen.santiago.findmeapp.data.model.User;
import com.guillen.santiago.findmeapp.view.Utils;
import com.guillen.santiago.findmeapp.view.careTaker.MainActivity;
import com.guillen.santiago.findmeapp.view.model.UserType;
import com.guillen.santiago.findmeapp.view.register.RegisterUserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAcitivty extends AppCompatActivity implements LoginContract.View {
    @BindView(R.id.et_email_login)
    protected EditText etEmailLogin;
    @BindView(R.id.et_password_login)
    protected EditText etPasswordLogin;
    @BindView(R.id.pbLoginUser)
    protected ProgressBar pbLoginUser;
    @BindView(R.id.swIsPatient)
    protected Switch swIsPatient;

    private LoginPresenter presenter;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void loginButtonClicked(){
        Utils.setLoadingView(pbLoginUser, true);
        presenter.loginUser(
                etEmailLogin.getText().toString(),
                etPasswordLogin.getText().toString(),
                swIsPatient.isChecked()
        );
    }

    @OnClick(R.id.btn_register)
    public void registerButtonClicked(){
        startActivityForResult(new Intent(LoginAcitivty.this, RegisterUserActivity.class), RegisterUserActivity.INTENT_CODE);
    }

    @Override
    public void onLoginSuccess(User userInfo) {
        presenter.setCurrentUser(userInfo);
        if(userInfo.getType().trim().equals(UserType.CARE_TAKER.getValue())){
            Intent intent = new Intent(LoginAcitivty.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {
            startActivity(new Intent(LoginAcitivty.this, com.guillen.santiago.findmeapp.view.patient.MainActivity.class));
        }
        Utils.setLoadingView(pbLoginUser, false);
    }

    @Override
    public void onLoginPatientSuccess(PatientModel patient) {
        User user = new User();
        user.setId(patient.getId());
        user.setType(UserType.PATIENT.getValue());
        user.setEmail(etEmailLogin.getText().toString());
        presenter.setCurrentUser(user);
        startActivity(new Intent(LoginAcitivty.this, com.guillen.santiago.findmeapp.view.patient.MainActivity.class));
        Utils.setLoadingView(pbLoginUser, false);
    }

    @Override
    public void onFailure(Exception e) {
        Utils.setLoadingView(pbLoginUser, false);
        Utils.createToastMessage(toast, this, "login Failed");
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RegisterUserActivity.INTENT_CODE){
            if(resultCode == RESULT_OK){
                Utils.createToastMessage(toast, this, "Usuario creado exitosamente");
            }else if(resultCode == RESULT_CANCELED) {
                Utils.createToastMessage(toast, this, "Registro de usuario fallido");
            }
        }
    }
}
